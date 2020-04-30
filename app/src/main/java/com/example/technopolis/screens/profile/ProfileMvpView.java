package com.example.technopolis.screens.profile;

import android.app.Activity;

import com.example.technopolis.profile.model.UserProfile;
import com.example.technopolis.screens.common.mvp.MvpViewObservable;
import com.example.technopolis.screens.common.nav.BackPressedListener;

public interface ProfileMvpView extends MvpViewObservable<ProfileMvpView.Listener> {

    interface Listener extends BackPressedListener {

        void onBtnGoBackClicked();

        void onLongClick(Activity activity, String text);

        void onGroupButtonClicked(long id);

        void onSignOutClicked();

    }

    void showBackButton(String backButtonText);

    void bindData(UserProfile userProfile);

    void showExitButton();

    void hideNavBar();

}
