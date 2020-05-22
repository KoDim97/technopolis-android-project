package com.example.technopolis.screens.scheduleritems;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Toast;

import com.example.technopolis.BaseActivity;
import com.example.technopolis.R;
import com.example.technopolis.api.ApiHelper;
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

import me.everything.android.ui.overscroll.IOverScrollStateListener;
import me.everything.android.ui.overscroll.IOverScrollUpdateListener;

import static me.everything.android.ui.overscroll.IOverScrollState.STATE_BOUNCE_BACK;
import static me.everything.android.ui.overscroll.IOverScrollState.STATE_DRAG_START_SIDE;

public class SchedulerItemsPresenter implements MvpPresenter<SchedulerItemsMvpView>,
        BackPressedListener {
    private static final String RESPONSE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private final ScreenNavigator screenNavigator;
    private final BackPressDispatcher backPressDispatcher;
    private final SchedulerItemService schedulerItemService;
    private final ThreadPoster mainThreadPoster;
    private final ApiHelper apiHelper;
    private final BaseActivity activity;

    private SchedulerItemsMvpView view;
    private Thread thread;
    private float currentOverScrollOffset;

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
        setOnReloadListener();
        loadItems();
    }

    private void setOnReloadListener() {
        IOverScrollStateListener overScrollStateListener = (decor, oldState, newState) -> {
            if (newState == STATE_BOUNCE_BACK) {
                if (oldState == STATE_DRAG_START_SIDE && currentOverScrollOffset > 100) {
                    new Thread(() -> {
                        final List<SchedulerItem> schedulerItems = schedulerItemService.requestFromApi();
                        final int actualDayPosition = calculateActualDayPosition(schedulerItems);
                        if (!showMessageIfExist()) {
                            final List<View.OnClickListener> listeners = createListeners(schedulerItems);
                            if (!thread.isInterrupted()) {
                                mainThreadPoster.post(() -> {
                                    onItemsLoaded(schedulerItems, listeners, actualDayPosition);
                                });
                            }
                        }
                    }).start();
                }
            }
        };
        IOverScrollUpdateListener overScrollUpdateListener = (decor, state, offset) -> {
            currentOverScrollOffset = offset;
        };
        view.setOnReloadListener(overScrollStateListener, overScrollUpdateListener);
    }


    private boolean showMessageIfExist() {
        Integer message = apiHelper.getMessage();
        if (message != null) {
            if (message == R.string.networkError) {
                if (!apiHelper.isOnline(activity)) {
                    activity.runOnUiThread(() -> Toast.makeText(activity, message, Toast.LENGTH_SHORT).show());
                }
            } else if (message == R.string.reloadRequest) {
                apiHelper.clear();
                schedulerItemService.reloadAuthToken();
                loadItems();
            } else if (message == R.string.authFailed) {
                activity.runOnUiThread(() -> screenNavigator.changeAuthorized(false));
            }
            return true;
        }
        return false;
    }

    private void loadItems() {
        thread = new Thread(() -> {
            final List<SchedulerItem> schedulerItems = schedulerItemService.items();
            showMessageIfExist();
            final int actualDayPosition = calculateActualDayPosition(schedulerItems);
            final List<View.OnClickListener> listeners = createListeners(schedulerItems);
            if (thread != null && !thread.isInterrupted()) {
                mainThreadPoster.post(() -> onItemsLoaded(schedulerItems, listeners, actualDayPosition));
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
        actualPosition--;
        actualPosition = Math.max(actualPosition, 0);
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
            if (showMessageIfExist()) {
                final List<View.OnClickListener> listeners = createListeners(schedulerItems);
                mainThreadPoster.post(() -> onItemsLoaded(schedulerItems, listeners, actualDayPosition));
            }
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
        return screenNavigator.navigateUp();
    }
}
