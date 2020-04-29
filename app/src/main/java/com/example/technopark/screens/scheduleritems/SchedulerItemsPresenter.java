package com.example.technopark.screens.scheduleritems;

import android.view.View;

import com.example.technopark.scheduler.model.SchedulerItem;
import com.example.technopark.scheduler.service.SchedulerItemService;
import com.example.technopark.screens.common.mvp.MvpPresenter;
import com.example.technopark.screens.common.nav.BackPressDispatcher;
import com.example.technopark.screens.common.nav.BackPressedListener;
import com.example.technopark.screens.common.nav.ScreenNavigator;
import com.example.technopark.util.ThreadPoster;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SchedulerItemsPresenter implements MvpPresenter<SchedulerItemsMvpView>,
        BackPressedListener {
    private static final String RESPONSE_FORMAT = "yyyy-MM-dd HH:mm:ss";

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
                final List<SchedulerItem> schedulerItems = schedulerItemService.items();
                final int actualDayPosition = calculateActualDayPosition(schedulerItems);
                final List<View.OnClickListener> listeners = createListeners(schedulerItems);
                if (!thread.isInterrupted()) {
                    mainThreadPoster.post(() -> onItemsLoaded(schedulerItems, listeners, actualDayPosition));
                }
            }
        });
        thread.start();
    }

    private int calculateActualDayPosition(List<SchedulerItem> schedulerItems) {
        int actualPosition = 0;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(RESPONSE_FORMAT, new Locale("ru"));
        String date = simpleDateFormat.format(new Date());
        for (SchedulerItem schedulerItem : schedulerItems) {
            if (date.compareTo(schedulerItem.getDate()) > 0) {
                actualPosition++;
            }
        }
        return actualPosition;
    }

    private void onItemsLoaded(List<SchedulerItem> schedulerItems, List<View.OnClickListener> listeners, int actualPosition) {
        view.bindData(schedulerItems, listeners, actualPosition);
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

    public void onCheckInClicked(long id) {
        thread = new Thread(() -> {
            final List<SchedulerItem> schedulerItems = schedulerItemService.checkInItem(id);
            final int actualDayPosition = calculateActualDayPosition(schedulerItems);
            final List<View.OnClickListener> listeners = createListeners(schedulerItems);
            mainThreadPoster.post(() -> onItemsLoaded(schedulerItems, listeners, actualDayPosition));
        });
        thread.start();
    }

    private List<View.OnClickListener> createListeners(List<SchedulerItem> items) {
        List<View.OnClickListener> listeners = new ArrayList<>();
        for (SchedulerItem schedulerItem : items) {
            listeners.add(v -> onCheckInClicked(schedulerItem.getId()));
        }
        return listeners;
    }

    @Override
    public boolean onBackPressed() {
        screenNavigator.navigateUp();
        return true;
    }
}
