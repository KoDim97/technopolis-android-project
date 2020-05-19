package com.example.technopolis.screens.root;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.technopolis.BaseActivity;
import com.example.technopolis.R;
import com.example.technopolis.screens.common.nav.ScreenNavigator;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MenuRootViewInitializer {
    private ScreenNavigator screenNavigator;
    private BottomNavigationView navigation;


    public MenuRootViewInitializer(BaseActivity rootView, ScreenNavigator screenNavigator) {
        this.screenNavigator = screenNavigator;
        navigation = (BottomNavigationView) rootView.findViewById(R.id.navigation);
        setup();
    }

    public void setNavElem(int index) {
        switch (index) {
            case 0:
                navigation.setSelectedItemId(R.id.navigation_news);
                return;
            case 1:
                navigation.setSelectedItemId(R.id.navigation_schedule);
                return;
            case 2:
                navigation.setSelectedItemId(R.id.navigation_profile);
                return;
        }
    }


    public void setBarVisible(int state) {
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
        BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                return screenNavigator.loadFragment(menuItem);
            }
        };
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
