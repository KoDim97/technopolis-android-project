package com.example.technopark.screens.common.nav;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.technopark.BaseActivity;
import com.example.technopark.R;
import com.example.technopark.screens.authorization.AuthorizationFragment;
import com.example.technopark.screens.grouplist.GroupListFragment;
import com.example.technopark.screens.profile.ProfileFragment;
import com.example.technopark.screens.root.MenuRootFragment;
import com.ncapdevi.fragnav.FragNavController;

public class ScreenNavigator implements FragNavController.RootFragmentListener {

    private final FragNavController fragNavController;
    private boolean authorized = false;
    private BaseActivity activity;
    private MenuRootFragment menuRootFragment;


    public ScreenNavigator(FragmentManager fragmentManager, Bundle savedInstanceState, BaseActivity activity) {
        this.activity = activity;
        fragNavController = new FragNavController(fragmentManager, R.id.container);
        fragNavController.setRootFragmentListener(this);
        fragNavController.initialize(FragNavController.TAB1, savedInstanceState);

    }

    @Override
    public int getNumberOfRootFragments() {
        return 1;
    }

    @Override
    public Fragment getRootFragment(int index) {
        if (!authorized) {
            return AuthorizationFragment.newInstance(activity);
        } else {
            menuRootFragment = MenuRootFragment.newInstance();
            return menuRootFragment;
        }
    }

    public void changeAuthorized(boolean authorized) {
        this.authorized = authorized;
        fragNavController.initialize(FragNavController.TAB1, null);
    }

    public void toGroupList(long id) {
        fragNavController.pushFragment(GroupListFragment.newInstance(id));
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
