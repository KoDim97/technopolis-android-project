package com.example.technopark.screens.common.mvp;

public interface MvpPresenter<T extends MvpView> {

    void bindView(T view) throws InterruptedException;

    void onStart();

    void onStop();

    void onDestroy();
}
