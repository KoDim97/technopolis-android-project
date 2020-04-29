package com.example.technopark.screens.profile;

import com.example.technopark.profile.model.UserProfile;
import com.example.technopark.screens.common.mvp.MvpViewObservable;
import com.example.technopark.screens.common.nav.BackPressedListener;

public interface ProfileMvpView extends MvpViewObservable<ProfileMvpView.Listener> {

    interface Listener extends BackPressedListener {

        void onBtnGoBackClicked();
    }

    void showProgress();

    void hideProgress();

    void bindData(UserProfile userProfile);

}
