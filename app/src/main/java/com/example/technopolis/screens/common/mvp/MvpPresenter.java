package com.example.technopolis.screens.common.mvp;

import com.example.technopolis.BaseActivity;
import com.example.technopolis.screens.common.nav.ScreenNavigator;

public interface MvpPresenter<T extends MvpView> {

    void bindView(T view) throws InterruptedException;

    void onTurnScreen(ScreenNavigator screenNavigator, BaseActivity activity);

    void onStart();

    void onStop();

    void onDestroy();
}
