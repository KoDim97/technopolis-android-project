package com.example.technopark;

import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.MenuItem;
import android.view.View;

import com.example.technopark.fragment.AuthorizationFragment;
import com.example.technopark.fragment.NewsFragment;
import com.example.technopark.fragment.ProfileFragment;
import com.example.technopark.fragment.SchedulerFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BaseActivity extends AppCompatActivity {

    private boolean authorized=false;
    private int visibility = View.VISIBLE;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener;
    private BottomNavigationView navigation;
    private void loadFragment(Fragment fragment) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fl_content, fragment);
            ft.commit();
    }

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
        AuthorizationFragment fragment=AuthorizationFragment.newInstance(this);
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
        loadFragment(NewsFragment.newInstance());
        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(authorized) {
            setContentView(R.layout.activity_base);
            BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
            mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

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
        }else{
            setContentView(R.layout.authorization);
            AuthorizationFragment fragment=AuthorizationFragment.newInstance(this);
        }
    }


}
