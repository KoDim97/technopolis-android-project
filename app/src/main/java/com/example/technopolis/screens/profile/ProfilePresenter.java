package com.example.technopolis.screens.profile;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.widget.Toast;

import com.example.technopolis.BaseActivity;
import com.example.technopolis.R;
import com.example.technopolis.api.ApiHelper;
import com.example.technopolis.profile.model.UserProfile;
import com.example.technopolis.profile.service.ProfileService;
import com.example.technopolis.screens.common.mvp.MvpPresenter;
import com.example.technopolis.screens.common.nav.BackPressDispatcher;
import com.example.technopolis.screens.common.nav.ScreenNavigator;
import com.example.technopolis.util.ThreadPoster;

import java.util.List;

public class ProfilePresenter implements MvpPresenter<ProfileMvpView>, ProfileMvpView.Listener {

    private static final String VK_APP_PACKAGE_ID = "com.vkontakte.android";
    private static final String FACEBOOK_APP_PACKAGE_ID = "com.facebook.katana";
    private static final String TELEGRAM_APP_PACKAGE_ID = "org.telegram.messenger";
    private static final String SKYPE_APP_PACKAGE_ID = "com.skype.raider";
    private static final String TAMTAM_APP_PACKAGE_ID = "chat.tamtam";
    private static final String MAIL_RU_APP_PACKAGE_ID = "ru.mail.mailapp";
    private static final String GITHUB_APP_PACKAGE_ID = "com.github";

    private final String userName;
    private final String backButtonText;

    private ProfileMvpView view;
    private final ProfileService profileService;
    private final ThreadPoster mainThreadPoster;
    private final ApiHelper apiHelper;
    private ScreenNavigator screenNavigator;
    private BackPressDispatcher backPressDispatcher;

    private Thread thread;
    private boolean isDataUpdated = false;

    public ProfilePresenter(String userName, String backButtonText, ProfileService profileService,
                            ScreenNavigator screenNavigator, ThreadPoster mainThreadPoster,
                            BackPressDispatcher backPressDispatcher, ApiHelper apiHelper) {
        this.userName = userName;
        this.backButtonText = backButtonText;
        this.profileService = profileService;
        this.screenNavigator = screenNavigator;
        this.mainThreadPoster = mainThreadPoster;
        this.backPressDispatcher = backPressDispatcher;
        this.apiHelper = apiHelper;
    }

    @Override
    public void bindView(ProfileMvpView view) {
        this.view = view;
        view.showProgress();
        loadItem();
    }

    @Override
    public void onTurnScreen(ScreenNavigator screenNavigator, BaseActivity activity) {
        this.screenNavigator = screenNavigator;
        this.backPressDispatcher = activity;
    }

    private void loadItem() {
        thread = new Thread(() -> {
            UserProfile userProfile = profileService.findByUserName(userName);
            apiHelper.showMessageIfExist(profileService.getApi(), screenNavigator, this::loadItem);
            if (thread != null && !thread.isInterrupted()) {
                if (userProfile != null) {
                    mainThreadPoster.post(() -> onItemLoaded(userProfile));
                } else {
                    if (!userName.equals("")) {
                        mainThreadPoster.post(this::onBackPressed);
                    }
                }
            }
        });
        thread.start();
    }

    public void updateData() {
        thread = new Thread(() -> {
            UserProfile userProfile = profileService.requestFromServer(userName);
            if (!apiHelper.showMessageIfExist(profileService.getApi(), screenNavigator, this::loadItem)) {
//                profileService.clear(userName);
                if (thread != null && !thread.isInterrupted()) {
                    if (userProfile != null) {
                        mainThreadPoster.post(() -> {
                            isDataUpdated = true;
                            onItemLoaded(userProfile);
                            ProfileFragment.handler.sendMessage(ProfileFragment.handler.obtainMessage());
                        });
                    }
                }
            } else {
                ProfileFragment.handler.sendMessage(ProfileFragment.handler.obtainMessage());
            }
        });
        thread.start();
    }

    private void onItemLoaded(UserProfile userProfile) {
        if (view != null) {
            view.bindData(userProfile);
            if (!backButtonText.equals("")) {
                view.showBackButton(backButtonText);
            }

//        Показываем кнопку "Выйти", если имеем дело с профилем пользователя
            if (userName.equals("")) {
                if (!isDataUpdated) {
                    view.showExitButton();
                    isDataUpdated = false;
                }
            } else {
//          Показываем имя пользователя в toolbar'e
                view.showNameOnToolbar(userProfile.getFullName());
            }
        }
    }

    public void onGroupButtonClicked(long id) {
        screenNavigator.toGroupList(id);
        isDataUpdated = false;
    }

    @Override
    public void onSignOutClicked() {
        screenNavigator.changeAuthorized(false);
    }

    @Override
    public void onStart() {
        view.registerListener(this);
        backPressDispatcher.registerListener(this);
    }

    @Override
    public void onStop() {
        view.unregisterListener(this);
        backPressDispatcher.unregisterListener(this);
    }

    @Override
    public void onDestroy() {
        // dispose all requests
        if (thread != null) {
            thread.interrupt();
        }
        thread = null;
        view = null;
    }


    @Override
    public void onBtnGoBackClicked() {
        screenNavigator.navigateUp();
    }

    private static Uri correctUri(String url, String name) {
        Uri uri = null;
        switch (name) {
            case "telegram":
                uri = Uri.parse("https://telegram.me/" + url);
                break;
            case "vkontakte":
                uri = Uri.parse("https://vk.com/" + url);
                break;
            case "odnoklassniki":
                uri = Uri.parse("https://ok.ru/profile/" + url);
                break;
            case "facebook":
                uri = Uri.parse("https://www.facebook.com/" + url);
                break;
            case "tamtam":
                uri = Uri.parse("https://tt.me/" + url);
                break;
            case "guthub":
                uri = Uri.parse("https://github.com/" + url);
                break;
            case "bitbucket":
                uri = Uri.parse("https://bitbucket.org/" + url);
                break;
            case "skype":
                uri = Uri.parse("skype:" + url + "?chat");
                break;
        }
        return uri;
    }

    private static void openMailApp(Activity activity, String mail, String packageName) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        if (!mail.contains("@mail.ru")) {
            mail += "@mail.ru";
        }
        intent.putExtra(Intent.EXTRA_EMAIL, mail);
        intent.setPackage(packageName);
        intent.setType("message/rfc822");

        List<ResolveInfo> resInfo = activity.getPackageManager().queryIntentActivities(intent, 0);

        boolean isContainApp = false;
        for (ResolveInfo info : resInfo) {
            if (info.activityInfo == null) continue;
            if (packageName.equals(info.activityInfo.packageName)) {
                isContainApp = true;
                break;
            }
        }
        if (isContainApp) {
            activity.startActivity(intent);
        }
    }

    private static boolean isAppFromTheList(ResolveInfo info) {
        return VK_APP_PACKAGE_ID.equals(info.activityInfo.packageName)
                || FACEBOOK_APP_PACKAGE_ID.equals(info.activityInfo.packageName)
                || TELEGRAM_APP_PACKAGE_ID.equals(info.activityInfo.packageName)
                || SKYPE_APP_PACKAGE_ID.equals(info.activityInfo.packageName)
                || TAMTAM_APP_PACKAGE_ID.equals(info.activityInfo.packageName)
                || GITHUB_APP_PACKAGE_ID.equals(info.activityInfo.packageName);
    }

    private static void openLink(Activity activity, String url, String name) {
        Uri uri = Uri.parse(url);
        if (uri.isRelative()) {
            if ("agent".equals(name)) {
                openMailApp(activity, url, MAIL_RU_APP_PACKAGE_ID);
                return;
            } else {
                uri = correctUri(url, name);
            }
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);

        List<ResolveInfo> resInfo = activity.getPackageManager().queryIntentActivities(intent, 0);

        if (resInfo.isEmpty()) return;

        for (ResolveInfo info : resInfo) {
            if (info.activityInfo == null) continue;
            if (isAppFromTheList(info)) {
                intent.setPackage(info.activityInfo.packageName);
                break;
            }
        }
        activity.startActivity(intent);
    }

    @Override
    public void onAccountClick(Activity activity, String text, String name) {
        openLink(activity, text, name);
    }

    private void openMailActivity(Activity activity, String email) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, email);
        intent.setType("message/rfc822");

        String title = activity.getResources().getString(R.string.choose_email_client);

        activity.startActivity(Intent.createChooser(intent, title));
    }

    private void openTelActivity(Activity activity, String telephone) {
        Intent intent = new Intent(
                Intent.ACTION_DIAL,
                Uri.parse("tel:" + telephone)
        );
        activity.startActivity(intent);
    }

    @Override
    public void onContactClick(Activity activity, String contact) {
        if (contact.contains("@")) {
            openMailActivity(activity, contact);
        } else {
            openTelActivity(activity, contact);
        }
    }

    @Override
    public void onMarksClick(Activity activity, String username) {
        String url = formUrlFromUserName(username);
        if (apiHelper.isOnline()) {
            screenNavigator.toFeedBack(url);
            isDataUpdated = false;
        } else {
            activity.runOnUiThread(() -> Toast.makeText(activity, R.string.networkError, Toast.LENGTH_SHORT).show());
        }
    }

    private String formUrlFromUserName(String username) {
        if (username.equals("")) {
            username = profileService.getApi().getUser().getUsername();
        }
        return profileService.getApi().getProjectUrl() + "/cabinet/" + username + "/progress/";
    }

    @Override
    public boolean onBackPressed() {
        return screenNavigator.navigateUp();
    }


}
