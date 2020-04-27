package com.example.technopark.root;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.technopark.BaseActivity;
import com.example.technopark.R;
import com.example.technopark.fragment.NewsFragment;
import com.example.technopark.fragment.ProfileFragment;
import com.example.technopark.fragment.SchedulerFragment;
import com.example.technopark.screens.common.nav.ScreenNavigator;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Root extends Fragment {

    private ScreenNavigator screenNavigator;

    private boolean authorized=false;
    private int visibility = View.VISIBLE;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener;
    private BottomNavigationView navigation;


    public static Root newInstance() {
        return new Root();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_base, container, false);
        navigation = (BottomNavigationView) getActivity().findViewById(R.id.navigation);
        setup();
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        screenNavigator = getMainActivity().getScreenNavigator();
    }
    private void setup() {
        mOnNavigationItemSelectedListener=new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_news:
                        loadFragment(NewsFragment.newInstance());
                        return true;
                    case R.id.navigation_schedule:
                        loadFragment(SchedulerFragment.newInstance());
                        return true;
                    case R.id.navigation_profile:
                        loadFragment(ProfileFragment.newInstance());
                        return true;
                }
                return false;
            }
        };
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
    private void loadFragment(Fragment fragment) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fl_content, fragment);
        ft.commit();
    }
    @Nullable private BaseActivity getMainActivity() {
        return (BaseActivity) getActivity();
    }
}
