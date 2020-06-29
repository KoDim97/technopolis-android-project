package com.example.technopolis;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.technopolis.log.LogHelper;
import com.example.technopolis.save.PauseController;
import com.example.technopolis.save.PauseControllerImpl;
import com.example.technopolis.screens.common.nav.BackPressDispatcher;
import com.example.technopolis.screens.common.nav.BackPressedListener;
import com.example.technopolis.screens.common.nav.ScreenNavigator;
import com.example.technopolis.screens.root.MenuRootViewInitializer;

import java.util.HashSet;
import java.util.Set;

public class BaseActivity extends AppCompatActivity implements BackPressDispatcher {
    private Set<BackPressedListener> backPressedListeners;
    private ScreenNavigator screenNavigator;
    private MenuRootViewInitializer rootViewController;
    private PauseController pauseController;

    private static final String CURRENT_NAV_ELEMENT_INDEX = "CURRENT_NAV_ELEMENT_INDEX";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_menu);
        backPressedListeners = new HashSet<>();
        pauseController = new PauseControllerImpl((App) getApplication());
        pauseController.authorized();
        screenNavigator = new ScreenNavigator(getSupportFragmentManager(), savedInstanceState, this);
        rootViewController = new MenuRootViewInitializer(this, screenNavigator);
        ((App) getApplication()).updatePresentersArgs(screenNavigator, this);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int currentNavElemIndex = savedInstanceState.getInt(CURRENT_NAV_ELEMENT_INDEX);
        rootViewController.setNavElem(currentNavElemIndex);
    }



    @NonNull
    public MenuRootViewInitializer getRootViewController() {
        return rootViewController;
    }

    public void setNavElem(final int index) {
        rootViewController.setNavElem(index);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        screenNavigator.onSaveInstanceState(outState);
        outState.putInt(CURRENT_NAV_ELEMENT_INDEX, rootViewController.getCurrentNavElemIndex());
    }

    @NonNull
    public ScreenNavigator getScreenNavigator() {
        return screenNavigator;
    }

    @Override
    public void onBackPressed() {
        boolean isBackPressConsumedByAnyListener = false;
        for (BackPressedListener listener : backPressedListeners) {
            if (listener.onBackPressed()) {
                isBackPressConsumedByAnyListener = true;
            }
        }
        if (!isBackPressConsumedByAnyListener) {
            super.onBackPressed();
        }
    }

    @Override
    public void registerListener(BackPressedListener listener) {
        backPressedListeners.add(listener);
    }

    @Override
    public void unregisterListener(BackPressedListener listener) {
        backPressedListeners.remove(listener);
    }

    @Override
    public void onPause() {
        super.onPause();
        pauseController.onPause();
        LogHelper.i(this, "app paused");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogHelper.i(this, "app resumed");
    }
}
