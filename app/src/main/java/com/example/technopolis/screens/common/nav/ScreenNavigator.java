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

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ScreenNavigator implements FragNavController.RootFragmentListener {

    private final FragNavController fragNavController;
    private boolean authorized = false;
    private BaseActivity activity;
    private Map<Integer, Integer> log = new TreeMap<>();
    private int currentNum;
    private boolean pop = false;


    public ScreenNavigator(FragmentManager fragmentManager, Bundle savedInstanceState, BaseActivity activity) {
        this.activity = activity;
        fragNavController = new FragNavController(fragmentManager, R.id.fl_content);
        fragNavController.setRootFragmentListener(this);
        fragNavController.initialize(FragNavController.TAB1, savedInstanceState);
        log.put(0, 0);

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
            return activity.getRootFragmentList().get(0);
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



    private void deleteLoop(int index) {
        boolean find = false;
        int n = log.size();
        for (int i = 0; i < n; i++) {
            if (find) {
                log.remove(i);
                fragNavController.popFragment();
            } else if (log.get(i) == index) {
                find = true;
            }
        }
    }

    public void loadFragment(Fragment fragment, int index) {
        if (pop) {
            pop = false;
            return;
        }
        if (!log.containsValue(index)) {
            log.put(log.size(), index);
            fragNavController.pushFragment(fragment);
        } else if (index != 0) {
            deleteLoop(index);
        } else {
            fragNavController.clearStack();
            log.clear();
            log.put(0, 0);
        }
    }


    private boolean navBarElem(Fragment fragment) {
        List temp = activity.getRootFragmentList();
        if (temp.get(1) == fragment) {
            currentNum = 1;
            return true;
        } else if (temp.get(2) == fragment) {
            currentNum = 2;
            return true;
        }
        return false;
    }


    public boolean navigateUp() {
        if (!fragNavController.isRootFragment()) {
            if (navBarElem(fragNavController.getCurrentFrag())) {
                pop = true;
                log.remove(log.size() - 1);
                if (log.size() > 0)
                    activity.setNavElem(log.get(log.size() - 1));
                else activity.setNavElem(0);
            }
            fragNavController.popFragment();
            return true;
        }
        return false;
    }
}