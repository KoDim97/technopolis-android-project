package com.example.technopark.screens.scheduleritems;

import com.example.technopark.scheduler.model.SchedulerItem;
import com.example.technopark.scheduler.service.SchedulerItemService;
import com.example.technopark.screens.common.mvp.MvpPresenter;
import com.example.technopark.screens.common.nav.BackPressDispatcher;
import com.example.technopark.screens.common.nav.BackPressedListener;
import com.example.technopark.screens.common.nav.ScreenNavigator;
import com.example.technopark.util.ThreadPoster;

import java.util.List;

public class SchedulerItemsPresenter implements MvpPresenter<SchedulerItemsMvpView>,
        BackPressedListener {

    private final ScreenNavigator screenNavigator;
    private final BackPressDispatcher backPressDispatcher;
    private final SchedulerItemService schedulerItemService;
    private final ThreadPoster mainThreadPoster;

    private SchedulerItemsMvpView view;
    private Thread thread;

    public SchedulerItemsPresenter(ScreenNavigator screenNavigator, BackPressDispatcher backPressDispatcher,
            SchedulerItemService schedulerItemService, ThreadPoster mainThreadPoster) {
        this.screenNavigator = screenNavigator;
        this.backPressDispatcher = backPressDispatcher;
        this.schedulerItemService = schedulerItemService;
        this.mainThreadPoster = mainThreadPoster;
    }

    @Override
    public void bindView(SchedulerItemsMvpView view) {
        this.view = view;
        loadItems();
    }

    private void loadItems() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                final List<SchedulerItem> schedulerItems = schedulerItemService.findAll();
                if (!thread.isInterrupted()) {
                    mainThreadPoster.post(new Runnable() {
                        @Override
                        public void run() {
                            onItemsLoaded(schedulerItems);
                        }
                    });
                }
            }
        });
        thread.start();
    }

    private void onItemsLoaded(List<SchedulerItem> schedulerItems) {
        view.bindData(schedulerItems);
    }

    @Override
    public void onStart() {
        view.registerListener(this);
        backPressDispatcher.registerListener(this);
    }

    @Override
    public void onStop() {
        view.unregisterListener(this);
        backPressDispatcher.unregisterListener(this);
    }

    @Override
    public void onDestroy() {
        thread.interrupt();
        thread = null;
        view = null;
    }

    @Override
    public boolean onBackPressed() {
        screenNavigator.navigateUp();
        return true;
    }
}
