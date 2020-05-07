package com.example.technopolis;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.technopolis.screens.common.nav.BackPressDispatcher;
import com.example.technopolis.screens.common.nav.BackPressedListener;
import com.example.technopolis.screens.common.nav.ScreenNavigator;
import com.example.technopolis.screens.root.MenuRootViewInitializer;

import java.util.HashSet;
import java.util.Set;

public class BaseActivity extends AppCompatActivity implements BackPressDispatcher {
    private final Set<BackPressedListener> backPressedListeners = new HashSet<>();
    private ScreenNavigator screenNavigator;
    private MenuRootViewInitializer rootViewController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_menu);
        screenNavigator = new ScreenNavigator(getSupportFragmentManager(), savedInstanceState, this);
        rootViewController = new MenuRootViewInitializer(this, screenNavigator);
    }


    public MenuRootViewInitializer getRootViewController() {
        return rootViewController;
    }

    public void setNavElem(int index) {
        rootViewController.setNavElem(index);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        screenNavigator.onSaveInstanceState(outState);
    }

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
}
