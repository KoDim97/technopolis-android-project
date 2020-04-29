package com.example.technopark.screens.root;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.technopark.R;
import com.example.technopark.fragment.NewsFragment;
import com.example.technopark.fragment.SchedulerFragment;
import com.example.technopark.screens.common.nav.ScreenNavigator;
import com.example.technopark.screens.profile.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MenuRootViewController {
    private ScreenNavigator screenNavigator;
    private int visibility = View.VISIBLE;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener;
    private BottomNavigationView navigation;
    private final static String CURRENT_USER = "current_user";


    public MenuRootViewController(View rootView, ScreenNavigator screenNavigator) {
        this.screenNavigator = screenNavigator;
        navigation = (BottomNavigationView) rootView.findViewById(R.id.navigation);
        setup();
        screenNavigator.loadFragment(NewsFragment.newInstance());
    }


    public void setBarVisible(int state) {
        switch (state) {
            case View.VISIBLE:
                navigation.setVisibility(View.VISIBLE);
                visibility = View.VISIBLE;
                break;
            case View.GONE:
                navigation.setVisibility(View.GONE);
                visibility = View.GONE;
                break;
            case View.INVISIBLE:
                navigation.setVisibility(View.INVISIBLE);
                visibility = View.INVISIBLE;
                break;
        }
    }

    public int getVisibility() {
        return visibility;
    }

    private void setup() {
        mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_news:
                        screenNavigator.loadFragment(NewsFragment.newInstance());
                        return true;
                    case R.id.navigation_schedule:
                        screenNavigator.loadFragment(SchedulerFragment.newInstance());
                        return true;
                    case R.id.navigation_profile:
                        screenNavigator.loadFragment(ProfileFragment.newInstance(CURRENT_USER));
                        return true;
                }
                return false;
            }
        };
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
