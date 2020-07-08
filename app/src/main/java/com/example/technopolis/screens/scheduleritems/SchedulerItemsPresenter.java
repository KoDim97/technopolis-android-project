package com.example.technopolis.screens.scheduleritems;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.technopolis.BaseActivity;
import com.example.technopolis.R;
import com.example.technopolis.api.ApiHelper;
import com.example.technopolis.log.LogHelper;
import com.example.technopolis.scheduler.model.SchedulerItem;
import com.example.technopolis.scheduler.service.SchedulerItemService;
import com.example.technopolis.screens.common.mvp.MvpPresenter;
import com.example.technopolis.screens.common.nav.BackPressDispatcher;
import com.example.technopolis.screens.common.nav.BackPressedListener;
import com.example.technopolis.screens.common.nav.ScreenNavigator;
import com.example.technopolis.util.ThreadPoster;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SchedulerItemsPresenter implements MvpPresenter<SchedulerItemsMvpView>,
        BackPressedListener {
    private static final String RESPONSE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static boolean isFeedbackFragmentWasOpened = false;

    private final SchedulerItemService schedulerItemService;
    private final ThreadPoster mainThreadPoster;
    private final ApiHelper apiHelper;
    private ScreenNavigator screenNavigator;
    private BackPressDispatcher backPressDispatcher;
    private BaseActivity activity;
    public Handler handler;

    private SchedulerItemsMvpView view;
    private Thread thread;

    public SchedulerItemsPresenter(ScreenNavigator screenNavigator, BaseActivity activity,
                                   SchedulerItemService schedulerItemService, ThreadPoster mainThreadPoster, ApiHelper apiHelper) {
        this.screenNavigator = screenNavigator;
        this.backPressDispatcher = activity;
        this.activity = activity;
        this.schedulerItemService = schedulerItemService;
        this.mainThreadPoster = mainThreadPoster;
        this.apiHelper = apiHelper;
    }

    @Override
    public void bindView(SchedulerItemsMvpView view) {
        this.view = view;
        view.showProgress();
        setOnReloadListener();
        loadItems();
    }

    @Override
    public void onTurnScreen(ScreenNavigator screenNavigator, BaseActivity activity) {
        this.screenNavigator = screenNavigator;
        this.activity = activity;
        this.backPressDispatcher = activity;
    }

    private void setOnReloadListener() {
        SwipeRefreshLayout swipeRefreshLayout = view.getRootView().findViewById(R.id.swiperefresh_scheduler);
        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                swipeRefreshLayout.setRefreshing(false);
                LogHelper.i(this, "data refreshed");
            }
        };
        swipeRefreshLayout.setOnRefreshListener(() -> {
            new Thread(() -> {
                final List<SchedulerItem> schedulerItems = schedulerItemService.requestFromApi();
                if (!apiHelper.showMessageIfExist(schedulerItemService.getApi(), screenNavigator, this::loadItems)) {
                    final List<View.OnClickListener> listeners = createCheckInListeners(schedulerItems);
                    final List<IsOnlineSupplier> suppliers = createEstimateSupplier(schedulerItems);
                    final int actualDayPosition = calculateActualDayPosition(schedulerItems);
                    if (thread != null && !thread.isInterrupted()) {
                        mainThreadPoster.post(() -> {
                            onItemsLoaded(schedulerItems, listeners, suppliers, actualDayPosition);
                            handler.sendMessage(handler.obtainMessage());
                        });
                    }
                } else {
                    handler.sendMessage(handler.obtainMessage());
                }
            }).start();
        });

    }

    private void loadItems() {
        thread = new Thread(() -> {
            List<SchedulerItem> schedulerItems = null;
            if (isFeedbackFragmentWasOpened) {
                schedulerItems = schedulerItemService.requestFromApi();
                if (!schedulerItems.isEmpty()) {
                    isFeedbackFragmentWasOpened = false;
                }
            }
            if (schedulerItems == null || schedulerItems.isEmpty()) {
                schedulerItems = schedulerItemService.items();
            }
            if (!apiHelper.showMessageIfExist(schedulerItemService.getApi(), screenNavigator, this::loadItems)) {
                final int actualDayPosition = calculateActualDayPosition(schedulerItems);
                final List<IsOnlineSupplier> suppliers = createEstimateSupplier(schedulerItems);
                final List<View.OnClickListener> listeners = createCheckInListeners(schedulerItems);
                final List<SchedulerItem> finalSchedulerItems = schedulerItems;
                if (thread != null && !thread.isInterrupted()) {
                    mainThreadPoster.post(() -> onItemsLoaded(finalSchedulerItems, listeners, suppliers, actualDayPosition));
                }
            }
        });
        thread.start();
    }

    private int calculateActualDayPosition(List<SchedulerItem> schedulerItems) {
        int actualPosition = 0;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(RESPONSE_FORMAT, new Locale("ru"));
        String date = simpleDateFormat.format(new Date());
        String previousDate = null;
        int amountOfLessonsInDay = 1;
        for (SchedulerItem schedulerItem : schedulerItems) {
            String schedulerDate = schedulerItem.getDate();
            if (date.compareTo(schedulerDate) > 0) {
                actualPosition++;
            } else {
                break;
            }
            if (schedulerDate.equals(previousDate)) {
                amountOfLessonsInDay++;
            } else {
                amountOfLessonsInDay = 1;
            }
            previousDate = schedulerDate;
        }
        actualPosition -= amountOfLessonsInDay;
        actualPosition = Math.max(actualPosition, 0);
        return actualPosition;
    }

    private void onItemsLoaded(List<SchedulerItem> schedulerItems, List<View.OnClickListener> listeners, List<IsOnlineSupplier> suppliers, int actualPosition) {
        if (view != null) {
            view.bindData(schedulerItems, listeners, suppliers, actualPosition);
        }
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
        if (thread != null) {
            thread.interrupt();
        }
        thread = null;
        view = null;
    }

    public void onCheckInClicked(long id) {
        thread = new Thread(() -> {
            final List<SchedulerItem> schedulerItems = schedulerItemService.checkInItem(id);
            if (!apiHelper.showMessageIfExist(schedulerItemService.getApi(), screenNavigator, this::loadItems)) {
                final int actualDayPosition = calculateActualDayPosition(schedulerItems);
                final List<View.OnClickListener> listeners = createCheckInListeners(schedulerItems);
                final List<IsOnlineSupplier> suppliers = createEstimateSupplier(schedulerItems);
                mainThreadPoster.post(() -> onItemsLoaded(schedulerItems, listeners, suppliers, actualDayPosition));
            }
        });
        thread.start();
    }

    private List<View.OnClickListener> createCheckInListeners(List<SchedulerItem> items) {
        List<View.OnClickListener> listeners = new ArrayList<>();
        for (SchedulerItem schedulerItem : items) {
            listeners.add(v -> {
                if (apiHelper.isOnline()) {
                    onCheckInClicked(schedulerItem.getId());
                } else {
                    activity.runOnUiThread(() -> Toast.makeText(activity, R.string.networkError, Toast.LENGTH_SHORT).show());
                }
            });
        }
        return listeners;
    }

    private List<IsOnlineSupplier> createEstimateSupplier(List<SchedulerItem> items) {
        List<IsOnlineSupplier> suppliers = new ArrayList<>();
        for (SchedulerItem schedulerItem : items) {
            suppliers.add(() -> {
                if (!apiHelper.isOnline()) {
                    activity.runOnUiThread(() -> Toast.makeText(activity, R.string.networkError, Toast.LENGTH_SHORT).show());
                } else {
                    String feedbackUrl = schedulerItem.getFeedbackUrl();
                    screenNavigator.toFeedBack(feedbackUrl);
                    isFeedbackFragmentWasOpened = true;
                }
            });
        }
        return suppliers;
    }

    @Override
    public boolean onBackPressed() {
        return screenNavigator.navigateUp();
    }
}
