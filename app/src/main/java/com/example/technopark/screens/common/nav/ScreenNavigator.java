package com.example.technopark.screens.common.nav;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.technopark.BaseActivity;
import com.example.technopark.R;
import com.example.technopark.screens.authorization.AuthorizationFragment;
import com.ncapdevi.fragnav.FragNavController;

public class ScreenNavigator implements FragNavController.RootFragmentListener {

    private final FragNavController fragNavController;

    private  BaseActivity activity;
    public ScreenNavigator(FragmentManager fragmentManager, Bundle savedInstanceState, BaseActivity activity) {
        fragNavController = new FragNavController(fragmentManager, R.id.container);
        fragNavController.setRootFragmentListener(this);
        fragNavController.initialize(FragNavController.TAB1, savedInstanceState);
        this.activity=activity;
    }

    @Override public int getNumberOfRootFragments() {
        return 1;
    }

    @Override public Fragment getRootFragment(int index) {
        //return RootFragment.newInstance();
        AuthorizationFragment fragment= (AuthorizationFragment) AuthorizationFragment.newInstance(activity);
        int a =5;
        return fragment;
    }

    public void onSaveInstanceState(Bundle outState) {
        fragNavController.onSaveInstanceState(outState);
    }
    public void loadFragment(Fragment fragment) {
        FragmentTransaction ft = fragNavController.getFragmentManagerForDialog().beginTransaction();
        ft.replace(R.id.fl_content, fragment);
        ft.commit();
    }

    public void navigateUp() {
        fragNavController.popFragment();
    }
}
