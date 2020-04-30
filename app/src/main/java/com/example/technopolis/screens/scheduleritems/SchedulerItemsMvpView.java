package com.example.technopolis.screens.scheduleritems;

import android.view.View;

import com.example.technopolis.scheduler.model.SchedulerItem;
import com.example.technopolis.screens.common.mvp.MvpViewObservable;
import com.example.technopolis.screens.common.nav.BackPressedListener;

import java.util.List;

public interface SchedulerItemsMvpView extends MvpViewObservable<BackPressedListener> {

    void bindData(List<SchedulerItem> schedulerItems, List<View.OnClickListener> listeners, int actualPosition);

}
