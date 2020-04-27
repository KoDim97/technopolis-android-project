package com.example.technopark;

import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.technopark.screens.authorization.AuthorizationFragment;
import com.example.technopark.fragment.NewsFragment;
import com.example.technopark.fragment.ProfileFragment;
import com.example.technopark.fragment.SchedulerFragment;
import com.example.technopark.screens.common.nav.BackPressDispatcher;
import com.example.technopark.screens.common.nav.BackPressedListener;
import com.example.technopark.screens.common.nav.ScreenNavigator;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashSet;
import java.util.Set;

public class BaseActivity extends AppCompatActivity implements BackPressDispatcher {
    private final Set<BackPressedListener> backPressedListeners = new HashSet<>();

    private ScreenNavigator screenNavigator;

    private boolean authorized=false;
    private int visibility = View.VISIBLE;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener;
    private BottomNavigationView navigation;




    public void setBarVisible(int state){
        switch (state){
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

    public int getVisibility(){
        return visibility;
    }

    public void setAuthorizationView() {
        authorized=false;
        setContentView(R.layout.authorization);
        AuthorizationFragment fragment= (AuthorizationFragment) AuthorizationFragment.newInstance(this);
    }

    public void setMenuView(){
        authorized=true;
        setContentView(R.layout.activity_base);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        mOnNavigationItemSelectedListener=new BottomNavigationView.OnNavigationItemSelectedListener(){
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
                        screenNavigator.loadFragment(ProfileFragment.newInstance());
                        return true;
                }
                return false;
            }
        };
        screenNavigator.loadFragment(NewsFragment.newInstance());
        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authorization);
        screenNavigator = new ScreenNavigator(getSupportFragmentManager(), savedInstanceState,this);
        if (authorized) {
            setContentView(R.layout.activity_base);
            BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
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
                            screenNavigator.loadFragment(ProfileFragment.newInstance());
                            return true;
                    }
                    return false;
                }
            };
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        } else {



        }
    }


    @Override protected void onSaveInstanceState(Bundle outState) {
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

    @Override public void registerListener(BackPressedListener listener) {
        backPressedListeners.add(listener);
    }

    @Override public void unregisterListener(BackPressedListener listener) {
        backPressedListeners.remove(listener);
    }
}
