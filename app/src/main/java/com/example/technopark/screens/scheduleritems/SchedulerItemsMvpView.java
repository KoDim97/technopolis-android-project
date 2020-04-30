package com.example.technopark.screens.scheduleritems;

import android.view.View;

import com.example.technopark.scheduler.model.SchedulerItem;
import com.example.technopark.screens.common.mvp.MvpViewObservable;
import com.example.technopark.screens.common.nav.BackPressedListener;

import java.util.List;

public interface SchedulerItemsMvpView extends MvpViewObservable<BackPressedListener> {

    void bindData(List<SchedulerItem> schedulerItems, List<View.OnClickListener> listeners, int actualPosition);

}
