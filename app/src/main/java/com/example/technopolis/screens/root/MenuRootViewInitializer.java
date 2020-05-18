package com.example.technopolis.screens.root;

import android.view.View;

import androidx.annotation.NonNull;

import com.example.technopolis.BaseActivity;
import com.example.technopolis.R;
import com.example.technopolis.screens.common.nav.ScreenNavigator;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MenuRootViewInitializer {
    private final ScreenNavigator screenNavigator;
    private final BottomNavigationView navigation;


    public MenuRootViewInitializer(@NonNull final BaseActivity rootView, @NonNull final ScreenNavigator screenNavigator) {
        this.screenNavigator = screenNavigator;
        navigation = rootView.findViewById(R.id.navigation);
        setup();
    }

    public void setNavElem(final int index) {
        switch (index) {
            case 0:
                navigation.setSelectedItemId(R.id.navigation_news);
                return;
            case 1:
                navigation.setSelectedItemId(R.id.navigation_schedule);
                return;
            case 2:
                navigation.setSelectedItemId(R.id.navigation_profile);
        }
    }


    public void setBarVisible(final int state) {
        switch (state) {
            case View.VISIBLE:
                navigation.setVisibility(View.VISIBLE);
                break;
            case View.GONE:
                navigation.setVisibility(View.GONE);
                break;
            case View.INVISIBLE:
                navigation.setVisibility(View.INVISIBLE);
                break;
        }
    }


    private void setup() {
        final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = screenNavigator::loadFragment;
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
