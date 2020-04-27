package com.example.technopark.screens.profile;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.technopark.BaseActivity;
import com.example.technopark.R;
import com.example.technopark.screens.common.mvp.MvpPresenter;

public class ProfilePresenter implements MvpPresenter<ProfileMvpView>, ProfileMvpView.Listener {
    private ClipboardManager myClipboard;
    private ClipData myClip;

    @Override
    public void bindView(ProfileMvpView view) {

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


}
