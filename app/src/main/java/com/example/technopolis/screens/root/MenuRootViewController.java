package com.example.technopolis.screens.root;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.technopolis.BaseActivity;
import com.example.technopolis.R;
import com.example.technopolis.screens.scheduleritems.SchedulerFragment;
import com.example.technopolis.screens.newsitems.NewsItemsFragment;
import com.example.technopolis.screens.common.nav.ScreenNavigator;
import com.example.technopolis.screens.profile.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MenuRootViewController {
    private ScreenNavigator screenNavigator;
    private int visibility = View.VISIBLE;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener;
    private BottomNavigationView navigation;
    private ArrayList<Fragment> fragments;


    public MenuRootViewController(BaseActivity rootView, ScreenNavigator screenNavigator) {
        this.screenNavigator = screenNavigator;
        navigation = (BottomNavigationView) rootView.findViewById(R.id.navigation);
        initListFragments();
        setup();
    }
    public void setNavElem(int index){
        switch (index){
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

    public List<Fragment> getRootFragmentList() {
        return fragments;
    }

    private void initListFragments() {
        fragments = new ArrayList<>();
        fragments.add(NewsItemsFragment.newInstance());
        fragments.add(SchedulerFragment.newInstance());
        fragments.add(ProfileFragment.newInstance());
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
                        screenNavigator.loadFragment(fragments.get(0),0);
                        return true;
                    case R.id.navigation_schedule:
                        screenNavigator.loadFragment(fragments.get(1),1);
                        return true;
                    case R.id.navigation_profile:
                        screenNavigator.loadFragment(fragments.get(2),2);
                        return true;
                }
                return false;
            }
        };
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
