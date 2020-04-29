package com.example.technopark.screens.profile;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.widget.Toast;

import com.example.technopark.R;
import com.example.technopark.profile.model.UserProfile;
import com.example.technopark.profile.service.ProfileService;
import com.example.technopark.screens.common.mvp.MvpPresenter;
import com.example.technopark.screens.common.nav.BackPressDispatcher;
import com.example.technopark.screens.common.nav.ScreenNavigator;
import com.example.technopark.util.ThreadPoster;

public class ProfilePresenter implements MvpPresenter<ProfileMvpView>, ProfileMvpView.Listener {

    private final String userName;

    private ProfileMvpView view;
    private final ProfileService profileService;
    private final ScreenNavigator screenNavigator;
    private final ThreadPoster mainThreadPoster;
    private final BackPressDispatcher backPressDispatcher;
    private Thread thread;
    private ClipboardManager myClipboard;
    private ClipData myClip;

    public ProfilePresenter(String userName, ProfileService profileService, ScreenNavigator screenNavigator, ThreadPoster mainThreadPoster, BackPressDispatcher backPressDispatcher) {
        this.userName = userName;
        this.profileService = profileService;
        this.screenNavigator = screenNavigator;
        this.mainThreadPoster = mainThreadPoster;
        this.backPressDispatcher = backPressDispatcher;
    }

    @Override
    public void bindView(ProfileMvpView view) {
        this.view = view;
        loadItem();
    }

    private void loadItem() {
        thread = new Thread(() -> {
            UserProfile userProfile = profileService.getUserProfile(userName);
            if (!thread.isInterrupted()) {
                mainThreadPoster.post(() -> onItemLoaded(userProfile));
            }
        });
        thread.start();
    }

    private void onItemLoaded(UserProfile userProfile) {
        view.bindData(userProfile);
    }

    public void onGroupButtonClicked(long id) {
        screenNavigator.toGroupList(id);
    }

    public void copyTextViewText(String text, Activity activity) {
        myClip = ClipData.newPlainText("text", text);
        provideClipboardManager(activity);
        myClipboard.setPrimaryClip(myClip);
        Toast.makeText(activity, R.string.copied, Toast.LENGTH_SHORT).show();
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
    public boolean onBackPressed() {
        screenNavigator.navigateUp();
        return true;
    }

    private void provideClipboardManager(Activity activity) {
        if (myClipboard == null) {
            myClipboard = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
        }
    }

}
