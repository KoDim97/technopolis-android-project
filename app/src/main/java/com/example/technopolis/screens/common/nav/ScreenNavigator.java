package com.example.technopolis.screens.common.nav;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.technopolis.App;
import com.example.technopolis.BaseActivity;
import com.example.technopolis.R;
import com.example.technopolis.screens.authorization.AuthorizationFragment;
import com.example.technopolis.screens.grouplist.GroupListFragment;
import com.example.technopolis.screens.newsitems.NewsItemsFragment;
import com.example.technopolis.screens.profile.ProfileFragment;
import com.example.technopolis.screens.scheduleritems.SchedulerFragment;
import com.ncapdevi.fragnav.FragNavController;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class ScreenNavigator implements FragNavController.RootFragmentListener {

    private final FragNavController fragNavController;
    private final BaseActivity activity;
    private final Map<Integer, Integer> log;
    private final App app;
    private boolean pop = false;
    private ArrayList<Fragment> fragments;
    private Fragment authorizationFragment;

    public ScreenNavigator(FragmentManager fragmentManager, Bundle savedInstanceState, @NonNull final BaseActivity activity) {
        this.activity = activity;
        authorizationFragment = AuthorizationFragment.newInstance(activity);
        app = (App) activity.getApplication();
        if (app.isAuthorized())
            initListFragments();
        fragNavController = new FragNavController(fragmentManager, R.id.fl_content);
        fragNavController.setRootFragmentListener(this);
        fragNavController.initialize(FragNavController.TAB1, savedInstanceState);
        log = new TreeMap<>();
        log.put(0, 0);

    }

    private void initListFragments() {
        fragments = new ArrayList<>();
        fragments.add(NewsItemsFragment.newInstance());
        fragments.add(SchedulerFragment.newInstance());
        fragments.add(ProfileFragment.newInstance());
    }

    @Override
    public int getNumberOfRootFragments() {
        return 1;
    }

    @NonNull
    @Override
    public Fragment getRootFragment(final int index) {
        if (!app.isAuthorized()) {
            fragments = null;
            return authorizationFragment;
        } else {
            if (fragments == null)
                initListFragments();
            return fragments.get(0);
        }
    }

    public void changeAuthorized(final boolean authorized) {
        if (app.isAuthorized() && fragNavController.getCurrentFrag() == fragments.get(0) && authorized)
            return;
        if (!authorized && !app.isAuthorized()) {
            return;
        }
        app.setAuthorized(authorized);
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

    private void deleteLoop(final int index) {
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

    private Integer getIndexByMenuItem(@NonNull final MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.navigation_news:
                return 0;
            case R.id.navigation_schedule:
                return 1;
            case R.id.navigation_profile:
                return 2;
            default:
                return null;
        }
    }

    public boolean loadFragment(@NonNull final MenuItem menuItem) {
        final Integer index = getIndexByMenuItem(menuItem);
        if (index == null)
            return false;
        if (pop) {
            pop = false;
            return true;
        }
        if (!log.containsValue(index)) {
            log.put(log.size(), index);
            fragNavController.pushFragment(fragments.get(index));
        } else if (index != 0) {
            deleteLoop(index);
        } else {
            fragNavController.clearStack();
            log.clear();
            log.put(0, 0);
        }
        return true;
    }

    private boolean navBarElem(final Fragment fragment) {
        if (fragments.get(1) == fragment) {
            return true;
        } else return fragments.get(2) == fragment;
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