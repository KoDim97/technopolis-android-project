package com.example.technopark.screens.common.nav;

public interface BackPressDispatcher {

    void registerListener(BackPressedListener listener);

    void unregisterListener(BackPressedListener listener);
}