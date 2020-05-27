package com.example.technopolis.screens.profile;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.widget.Toast;

import com.example.technopolis.BaseActivity;
import com.example.technopolis.R;
import com.example.technopolis.api.ApiHelper;
import com.example.technopolis.api.MailApi;
import com.example.technopolis.profile.model.UserProfile;
import com.example.technopolis.profile.service.ProfileService;
import com.example.technopolis.screens.common.mvp.MvpPresenter;
import com.example.technopolis.screens.common.nav.BackPressDispatcher;
import com.example.technopolis.screens.common.nav.ScreenNavigator;
import com.example.technopolis.util.ThreadPoster;

public class ProfilePresenter implements MvpPresenter<ProfileMvpView>, ProfileMvpView.Listener {

    private final String userName;
    private final String backButtonText;

    private ProfileMvpView view;
    private final ProfileService profileService;
    private final ScreenNavigator screenNavigator;
    private final ThreadPoster mainThreadPoster;
    private final BackPressDispatcher backPressDispatcher;
    private final ApiHelper apiHelper;
    private final BaseActivity activity;

    private Thread thread;
    private ClipboardManager myClipboard;
    private ClipData myClip;

    public ProfilePresenter(String userName, String backButtonText, ProfileService profileService,
                            ScreenNavigator screenNavigator, ThreadPoster mainThreadPoster,
                            BackPressDispatcher backPressDispatcher, ApiHelper apiHelper,
                            BaseActivity activity) {
        this.userName = userName;
        this.backButtonText = backButtonText;
        this.profileService = profileService;
        this.screenNavigator = screenNavigator;
        this.mainThreadPoster = mainThreadPoster;
        this.backPressDispatcher = backPressDispatcher;
        this.apiHelper = apiHelper;
        this.activity = activity;
    }

    @Override
    public void bindView(ProfileMvpView view) {
        this.view = view;
        loadItem();
    }

    private void loadItem() {
        thread = new Thread(() -> {
            UserProfile userProfile = profileService.findByUserName(userName);
            apiHelper.showMessageIfExist(activity, profileService.getApi(), screenNavigator, this::loadItem);
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

    @Override
    public void onLongClick(Activity activity, String text) {
        myClip = ClipData.newPlainText("text", text);
        provideClipboardManager(activity);
        myClipboard.setPrimaryClip(myClip);
        Toast.makeText(activity, R.string.copied, Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onBackPressed() {
        return screenNavigator.navigateUp();
    }

    private void provideClipboardManager(Activity activity) {
        if (myClipboard == null) {
            myClipboard = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
        }
    }


}
