package com.example.technopolis.screens.profile;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;

import com.example.technopolis.api.ApiHelper;
import com.example.technopolis.profile.model.UserProfile;
import com.example.technopolis.profile.service.ProfileService;
import com.example.technopolis.screens.common.mvp.MvpPresenter;
import com.example.technopolis.screens.common.nav.BackPressDispatcher;
import com.example.technopolis.screens.common.nav.ScreenNavigator;
import com.example.technopolis.util.ThreadPoster;

import java.util.List;

public class ProfilePresenter implements MvpPresenter<ProfileMvpView>, ProfileMvpView.Listener {

    private final String userName;
    private final String backButtonText;

    private ProfileMvpView view;
    private final ProfileService profileService;
    private final ScreenNavigator screenNavigator;
    private final ThreadPoster mainThreadPoster;
    private final BackPressDispatcher backPressDispatcher;
    private final ApiHelper apiHelper;

    private Thread thread;
    private ClipboardManager myClipboard;
    private ClipData myClip;

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
        loadItem();
    }

    private void loadItem() {
        thread = new Thread(() -> {
            UserProfile userProfile = profileService.findByUserName(userName);
            apiHelper.showMessageIfExist(profileService.getApi(), screenNavigator, this::loadItem);
            if (thread != null && !thread.isInterrupted()) {
                if (userProfile != null) {
                    mainThreadPoster.post(() -> onItemLoaded(userProfile));
                } else {
                    onBackPressed();
                }
            }
        });
        thread.start();
    }

    private void onItemLoaded(UserProfile userProfile) {
        view.bindData(userProfile);
        if (!backButtonText.equals("")) {
            view.showBackButton(backButtonText);
        }

//        Показываем кнопку "Выйти", если имеем дело с профилем пользователя
        if (userName.equals("")) {
            view.showExitButton();
        } else {
//          Показываем имя пользователя в toolbar'e
            view.showNameOnToolbar(userProfile.getFullName());
        }
    }

    public void onGroupButtonClicked(long id) {
        screenNavigator.toGroupList(id);
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
        thread.interrupt();
        thread = null;
        view = null;
    }


    @Override
    public void onBtnGoBackClicked() {
        screenNavigator.navigateUp();
    }

    private static final String VK_APP_PACKAGE_ID = "com.vkontakte.android";
    private static final String FACEBOOK_APP_PACKAGE_ID = "com.facebook.katana";
    private static final String TELEGRAM_APP_PACKAGE_ID = "org.telegram.messenger";
    private static final String  SKYPE_APP_PACKAGE_ID = "com.skype.raider";
    private static final String  TAMTAM_APP_PACKAGE_ID = "chat.tamtam";
    private static final String MAIL_RU_APP_PACKAGE_ID = "ru.mail.mailapp";


    private static void openLink(Activity activity, String url, String name) {
        Uri uri = Uri.parse(url);
        if (uri.isRelative()) {
            switch (name) {
                case "telegram":
                    uri = Uri.parse("https://telegram.me/" + url);
                    break;
                case "skype":
                    uri = Uri.parse("skype:" + url + "?chat");
                    break;
                case "agent":
                    openMailApp(activity, url, MAIL_RU_APP_PACKAGE_ID);
                    return;
            }
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);

        List<ResolveInfo> resInfo = activity.getPackageManager().queryIntentActivities(intent, 0);

        if (resInfo.isEmpty()) return;

        for (ResolveInfo info: resInfo) {
            if (info.activityInfo == null) continue;
            if (VK_APP_PACKAGE_ID.equals(info.activityInfo.packageName)
                    || FACEBOOK_APP_PACKAGE_ID.equals(info.activityInfo.packageName)
                    || TELEGRAM_APP_PACKAGE_ID.equals(info.activityInfo.packageName)
                    || SKYPE_APP_PACKAGE_ID.equals(info.activityInfo.packageName)
                    || TAMTAM_APP_PACKAGE_ID.equals(info.activityInfo.packageName)
            ) {
                intent.setPackage(info.activityInfo.packageName);
                break;
            }
        }
        activity.startActivity(intent);
    }

    private static void openMailApp(Activity activity, String mail, String packageName) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, mail +  "@mail.ru");
        intent.setPackage(packageName);
        intent.setType("message/rfc822");
        activity.startActivity(intent);
    }

    @Override
    public void onContactClick(Activity activity, String text, String name) {
        openLink(activity, text, name);
    }

    @Override
    public boolean onBackPressed() {
        return screenNavigator.navigateUp();
    }


}
