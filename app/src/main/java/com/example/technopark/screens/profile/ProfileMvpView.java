package com.example.technopark.screens.profile;

import android.app.Activity;

import com.example.technopark.profile.model.UserProfile;
import com.example.technopark.screens.common.mvp.MvpViewObservable;
import com.example.technopark.screens.common.nav.BackPressedListener;

public interface ProfileMvpView extends MvpViewObservable<ProfileMvpView.Listener> {

    interface Listener extends BackPressedListener {

        void onBtnGoBackClicked();

        void onLongClick(Activity activity, String text);

        void onGroupButtonClicked(long id);

    }

    void showBackButton(String backButtonText);

    void bindData(UserProfile userProfile);

}
