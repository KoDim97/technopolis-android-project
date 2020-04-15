package com.example.technopark;

import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BaseActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener;
    private BottomNavigationView navigation;
    private int visibility = View.VISIBLE;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        mOnNavigationItemSelectedListener=new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch ( menuItem.getItemId()) {
                    case R.id.navigation_news:
                        loadFragment(TestFragment1.newInstance());
                        return true;
                    case R.id.navigation_schedule:
                        loadFragment(TestFragment2.newInstance());
                        return true;
                    case R.id.navigation_profile:
                        loadFragment(ProfileFragment.newInstance());
                        return true;
                }
                return false;
            }
        };
        loadFragment(TestFragment1.newInstance());
        navigation = findViewById(R.id.navigation);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


}
