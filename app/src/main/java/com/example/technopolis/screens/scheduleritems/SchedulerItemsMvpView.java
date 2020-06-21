package com.example.technopolis.screens.scheduleritems;

import android.view.View;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.technopolis.scheduler.model.SchedulerItem;
import com.example.technopolis.screens.common.mvp.MvpViewObservable;
import com.example.technopolis.screens.common.nav.BackPressedListener;

import java.util.List;

import me.everything.android.ui.overscroll.IOverScrollStateListener;
import me.everything.android.ui.overscroll.IOverScrollUpdateListener;

public interface SchedulerItemsMvpView extends MvpViewObservable<BackPressedListener> {

    void bindData(List<SchedulerItem> schedulerItems, List<View.OnClickListener> listeners, List<IsOnlineSupplier> suppliers, int actualPosition);

//    void setOnReloadListener(IOverScrollStateListener overScrollStateListener, IOverScrollUpdateListener overScrollUpdateListener);

}
