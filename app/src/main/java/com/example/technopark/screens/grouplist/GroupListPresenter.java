package com.example.technopark.screens.grouplist;

import com.example.technopark.group.model.GroupItem;
import com.example.technopark.group.service.FindGroupItemService;
import com.example.technopark.screens.common.mvp.MvpPresenter;
import com.example.technopark.screens.common.nav.BackPressDispatcher;
import com.example.technopark.screens.common.nav.ScreenNavigator;
import com.example.technopark.screens.profile.ProfileFragment;
import com.example.technopark.util.ThreadPoster;

import java.util.List;

public class GroupListPresenter implements MvpPresenter<GroupListMvpView>,
        GroupListMvpView.Listener {

    private final long id;
    private final ScreenNavigator screenNavigator;
    private final BackPressDispatcher backPressDispatcher;
    private final FindGroupItemService findGroupItemService;
    private final ThreadPoster mainThreadPoster;

    private GroupListMvpView view;
    private Thread thread;

    public GroupListPresenter(long id, ScreenNavigator screenNavigator, BackPressDispatcher backPressDispatcher,
                              FindGroupItemService findGroupItemService, ThreadPoster mainThreadPoster) {
        this.id = id;
        this.screenNavigator = screenNavigator;
        this.backPressDispatcher = backPressDispatcher;
        this.findGroupItemService = findGroupItemService;
        this.mainThreadPoster = mainThreadPoster;
    }

    @Override public void bindView(GroupListMvpView view) {
        this.view = view;
        loadItems();
    }

    private void loadItems() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                final GroupItem groupItem = findGroupItemService.findById(id);
                if (!thread.isInterrupted()) {
                    mainThreadPoster.post(new Runnable() {
                        @Override
                        public void run() {
                            GroupListPresenter.this.onItemsLoaded(groupItem);
                        }
                    });
                }
            }
        });
        thread.start();
    }

    private void onItemsLoaded(GroupItem groupItem) {
        // prepare to show
        view.bindData(groupItem);
    }

    @Override public void onStart() {
        view.registerListener(this);
        backPressDispatcher.registerListener(this);
    }

    @Override public void onStop() {
        view.unregisterListener(this);
        backPressDispatcher.unregisterListener(this);
    }

    @Override public void onDestroy() {
        // dispose all requests
        thread.interrupt();
        thread = null;
        view = null;
    }

    @Override public boolean onBackPressed() {
        screenNavigator.navigateUp();
        return true;
    }

    @Override
    public void onStudentClicked(long studentId) {
        //temp
        screenNavigator.loadFragment(ProfileFragment.newInstance());
    }
}
