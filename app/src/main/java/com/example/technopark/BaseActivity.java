package com.example.technopark;

import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BaseActivity extends AppCompatActivity {

    private boolean authorized=false;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener;
    private void loadFragment(Fragment fragment) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fl_content, fragment);
            ft.commit();
    }

    void setAuthorizationView() {
        authorized=false;
        setContentView(R.layout.authorization);
        AuthorizationFragment fragment=AuthorizationFragment.newInstance(this);
    }

    void setMenuView(){
        authorized=true;
        setContentView(R.layout.activity_base);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        mOnNavigationItemSelectedListener=new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_news:
                        loadFragment(TestFragment1.newInstance());
                        return true;
                    case R.id.navigation_schedule:
                        loadFragment(TestFragment2.newInstance());
                        return true;
                    case R.id.navigation_profile:
                        loadFragment(TestFragment3.newInstance());
                        return true;
                }
                return false;
            }
        };
        loadFragment(TestFragment1.newInstance());


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
                            loadFragment(TestFragment1.newInstance());
                            return true;
                        case R.id.navigation_schedule:
                            loadFragment(TestFragment2.newInstance());
                            return true;
                        case R.id.navigation_profile:
                            loadFragment(TestFragment3.newInstance());
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
