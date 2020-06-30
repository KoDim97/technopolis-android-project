package com.example.technopolis.screens.common.nav;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.technopolis.App;
import com.example.technopolis.BaseActivity;
import com.example.technopolis.R;
import com.example.technopolis.log.FeedbackEmail;
import com.example.technopolis.log.LogHelper;
import com.example.technopolis.screens.authorization.AuthorizationFragment;
import com.example.technopolis.screens.common.FeedbackFragment;
import com.example.technopolis.screens.grouplist.GroupListFragment;
import com.example.technopolis.screens.newsitems.NewsItemsFragment;
import com.example.technopolis.screens.profile.ProfileFragment;
import com.example.technopolis.screens.root.MenuRootViewInitializer;
import com.example.technopolis.screens.scheduleritems.SchedulerFragment;
import com.ncapdevi.fragnav.FragNavController;

import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;

public class ScreenNavigator implements FragNavController.RootFragmentListener {

    private final FragNavController fragNavController;
    private final BaseActivity activity;
    private final App app;
    private Map<Integer, Integer> log;
    private boolean pop = false;
    private static ArrayList<Fragment> fragments;
    private int profileCounter = 0;
    private Fragment authorizationFragment;

    public ScreenNavigator(FragmentManager fragmentManager, Bundle savedInstanceState, @NonNull final BaseActivity activity) {
        this.activity = activity;
        authorizationFragment = AuthorizationFragment.newInstance();
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
        if (fragments == null) {
            fragments = new ArrayList<>();
            fragments.add(SchedulerFragment.newInstance());
            fragments.add(NewsItemsFragment.newInstance());
            fragments.add(ProfileFragment.newInstance());
        }
        if (app.provideApiHelper().isOnline()) {
            app.preload();
        }
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
        // if already authorized and @authorized return
        if (app.isAuthorized() && fragNavController.getCurrentFrag() == fragments.get(0) && authorized)
            return;
        // if already not authorized and !@authorized return
        if (!authorized && !app.isAuthorized()) {
            return;
        }
        //if !@authorized set first view and set app !authorized
        if (!authorized) {
            activity.getRootViewController().setNavElem(0);
        }
        app.setAuthorized(authorized);
        fragNavController.initialize(FragNavController.TAB1, null);
    }

    public void toGroupList(long id) {
        fragNavController.pushFragment(GroupListFragment.newInstance(id));
    }

    public void toFeedBack(String url) {
        fragNavController.pushFragment(FeedbackFragment.newInstance(url));
        LogHelper.i(this, "opened feedback frag");
    }

    public void toProfile(String username, String groupname) {
        if (fragNavController.getSize() > 10) {
            activity.runOnUiThread(() -> Toast.makeText(activity, R.string.stackError, Toast.LENGTH_SHORT).show());
        } else {
            fragNavController.pushFragment(ProfileFragment.newInstance(username, groupname));
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        fragNavController.onSaveInstanceState(outState);
    }

    /**
     * delete loop of navigation, when we go back at the elem at index
     */
    private void deleteLoop(final int index) {
        boolean find = false;
        int n = log.size();
        for (int i = 0; i < n; i++) {
            if (find) {
                log.remove(i);
                fragNavController.popFragment();
                fragNavController.executePendingTransactions();
            } else if (log.get(i) == index) {
                find = true;
            }
        }
    }

    private void provideLogs() {
        final Dialog logDialog = new Dialog(fragments.get(2).getContext(), R.style.ExitDialogAnimation);
        logDialog.getWindow().getAttributes().windowAnimations = R.style.ExitDialogAnimation;
        logDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(50, 0, 0, 0)));
        logDialog.setContentView(R.layout.log_popup_view);
        logDialog.setCancelable(true);
        logDialog.show();

        logDialog.findViewById(R.id.sendButton).setOnClickListener(v12 -> {
            logDialog.dismiss();
            //send logs
            new FeedbackEmail(activity)
                    .setSubject("Feedback")
                    .cacheAttach(LogHelper.FILENAME)
                    .build()
                    .send();
        });
        logDialog.findViewById(R.id.closeButton).setOnClickListener(v1 -> logDialog.dismiss());
    }

    public void setFragmentsStack(Stack<Fragment> fragmentsStack) {
        fragNavController.clearStack();
        boolean isFirst = true;
        for (Fragment fragment : fragmentsStack) {
            if (isFirst) {
                isFirst = false;
                continue;
            }
            fragNavController.pushFragment(fragment);
        }
        if (FeedbackFragment.isOpen && !((App) activity.getApplication()).provideApiHelper().isOnline()) {
            fragNavController.popFragment();
            activity.runOnUiThread(() -> Toast.makeText(activity, R.string.networkError, Toast.LENGTH_SHORT).show());
        }
    }

    public void setLog(Map<Integer, Integer> log) {
        this.log = log;
    }

    public Stack<Fragment> provideCurrentStack() {
        return fragNavController.getCurrentStack();
    }

    public Map<Integer, Integer> provideLog() {
        return log;
    }

    private Integer getIndexByMenuItem(@NonNull final MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.navigation_schedule:
                return 0;
            case R.id.navigation_news:
                return 1;
            case R.id.navigation_profile:
                return 2;
            default:
                return null;
        }
    }

    /**
     * load fragment view from fragment list
     *
     * @return true if fragment list contains @menuItem else false
     */

    public synchronized boolean loadFragment(@NonNull final MenuItem menuItem) {
        final Integer index = getIndexByMenuItem(menuItem);
        if (index == null)
            return false;
        //if back press
        if (pop) {
            pop = false;
            return true;
        }
        //if were not on this fragment
        if (!log.containsValue(index)) {
            LogHelper.i(this, "Switched to fragment " + index);
            profileCounter = 0;
            log.put(log.size(), index);
            fragNavController.pushFragment(fragments.get(index));
            fragNavController.executePendingTransactions();

            //if not root
        } else if (index != 0) {
            LogHelper.i(this, "Switched to fragment " + index);
            deleteLoop(index);
            if (index == 2) {
                profileCounter++;
            }
            //if root
        } else {
            LogHelper.i(this, "Switched to fragment " + index);
            profileCounter = 0;
            fragNavController.clearStack();
            fragNavController.executePendingTransactions();
            log.clear();
            log.put(0, 0);
        }

        if (profileCounter == 5) {
            profileCounter = 0;
            provideLogs();
        }

        MenuRootViewInitializer.currentNavElemIndex = index;
        return true;
    }

    /**
     * Check fragment is one of fragments at bottom navigation view ( list fragments)
     *
     * @return true if @fragment is one of fragments at bottom navigation view else false
     */
    private boolean navBarElem(final Fragment fragment) {
        if (fragments.get(1) == fragment) {
            return true;
        } else return fragments.get(2) == fragment;
    }

    //processing backpress
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