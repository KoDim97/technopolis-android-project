package com.example.technopolis;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.technopolis.screens.common.nav.BackPressDispatcher;
import com.example.technopolis.screens.common.nav.BackPressedListener;
import com.example.technopolis.screens.common.nav.ScreenNavigator;
import com.example.technopolis.screens.root.MenuRootViewController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BaseActivity extends AppCompatActivity implements BackPressDispatcher {
    private final Set<BackPressedListener> backPressedListeners = new HashSet<>();
    private ScreenNavigator screenNavigator;
    private MenuRootViewController rootViewController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_menu);
        screenNavigator = new ScreenNavigator(getSupportFragmentManager(), savedInstanceState, this);
        rootViewController = new MenuRootViewController(this, screenNavigator);
    }


    public MenuRootViewController getRootViewController() {
        return rootViewController;
    }
    public void setNavElem(int index){
        rootViewController.setNavElem(index);
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

    public List<Fragment> getRootFragmentList(){
        return rootViewController.getRootFragmentList();
    }
}
