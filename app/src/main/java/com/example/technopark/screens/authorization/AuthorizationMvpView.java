package com.example.technopark.screens.authorization;

import com.example.technopark.screens.common.mvp.MvpViewObservable;
import com.example.technopark.screens.common.nav.BackPressedListener;

public interface AuthorizationMvpView extends MvpViewObservable<AuthorizationMvpView.Listener> {
    interface Listener extends BackPressedListener {
        void onBtnEnterClicked();
    }
}
