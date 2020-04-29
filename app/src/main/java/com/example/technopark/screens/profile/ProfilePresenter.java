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
import com.example.technopark.util.ThreadPoster;

public class ProfilePresenter implements MvpPresenter<ProfileMvpView>, ProfileMvpView.Listener {

    private ProfileMvpView view;
    private final ProfileService profileService;
    private final ThreadPoster mainThreadPoster;
    private Thread thread;
    private ClipboardManager myClipboard;
    private ClipData myClip;

    public ProfilePresenter(ProfileService profileService, ThreadPoster mainThreadPoster) {
        this.profileService = profileService;
        this.mainThreadPoster = mainThreadPoster;

    }

    @Override
    public void bindView(ProfileMvpView view) {
        this.view = view;
        loadItem();
    }

    private void loadItem() {
        thread = new Thread(() -> {
            UserProfile userProfile = profileService.getCurrentUserProfile();
            if (!thread.isInterrupted()) {
                mainThreadPoster.post(() -> onItemLoaded(userProfile));
            }
        });
        thread.start();
    }

    private void onItemLoaded(UserProfile userProfile) {
        view.bindData(userProfile);
    }

    public void onGroupButtonClicked(
            /* Возможно нужно добавить аргументы, но не использую android View*/) {
//       TODO: @KoDim97 Переход на GroupList
    }

    public void copyTextViewText(String text, Activity activity) {
        myClip = ClipData.newPlainText("text", text);
        provideClipboardManager(activity);
        myClipboard.setPrimaryClip(myClip);
        Toast.makeText(activity, R.string.copied, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }


    @Override
    public void onBtnGoBackClicked() {

    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    private void provideClipboardManager(Activity activity) {
        if (myClipboard == null) {
            myClipboard = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
        }
    }

}
