package com.example.technopolis.screens.common.nav;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.technopolis.BaseActivity;
import com.example.technopolis.R;
import com.example.technopolis.screens.authorization.AuthorizationFragment;
import com.example.technopolis.screens.grouplist.GroupListFragment;
import com.example.technopolis.screens.profile.ProfileFragment;
import com.ncapdevi.fragnav.FragNavController;

public class ScreenNavigator implements FragNavController.RootFragmentListener {

    private final FragNavController fragNavController;
    private boolean authorized = false;
    private BaseActivity activity;


    public ScreenNavigator(FragmentManager fragmentManager, Bundle savedInstanceState, BaseActivity activity) {
        this.activity = activity;
        fragNavController = new FragNavController(fragmentManager, R.id.fl_content);
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
            return activity.getRootFragment();
        }
    }

    public void changeAuthorized(boolean authorized) {
        this.authorized = authorized;
        fragNavController.initialize(FragNavController.TAB1, null);
    }

    public void toGroupList(long id) {
        fragNavController.pushFragment(GroupListFragment.newInstance(id));
    }

    public void toProfile(String username, String groupname) {
        fragNavController.pushFragment(ProfileFragment.newInstance(username, groupname));
    }


    public void onSaveInstanceState(Bundle outState) {
        fragNavController.onSaveInstanceState(outState);
    }

    public void loadFragment(Fragment fragment) {
        fragNavController.replaceFragment(fragment);
    }

    public boolean navigateUp() {
        if (!fragNavController.isRootFragment()) {
            fragNavController.popFragment();
            return true;
        } else return false;
    }
}