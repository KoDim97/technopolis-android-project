package com.example.technopark.screens.authorization;

import com.example.technopark.screens.common.mvp.MvpPresenter;
import com.example.technopark.screens.common.nav.BackPressDispatcher;
import com.example.technopark.screens.common.nav.ScreenNavigator;
import com.example.technopark.util.ThreadPoster;

public class AuthorizationPresenter implements MvpPresenter<AuthorizationMvpView>,
        AuthorizationMvpView.Listener {
  //  private final ScreenNavigator screenNavigator;
    private final BackPressDispatcher backPressDispatcher;
    private static String login;
    private static String password;

    private AuthorizationMvpView view;

    public AuthorizationPresenter(BackPressDispatcher backPressDispatcher) { //ScreenNavigator screenNavigator, BackPressDispatcher backPressDispatcher) {
      //  this.screenNavigator = screenNavigator;
        this.backPressDispatcher = backPressDispatcher;
    }

    @Override
    public void bindView(AuthorizationMvpView view) {
        this.view = view;
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
        view = null;
    }

    @Override
    public boolean onBackPressed() {
     //   screenNavigator.navigateUp();
        return false;
    }

    @Override
    public void onBtnEnterClicked() {

    }
}
