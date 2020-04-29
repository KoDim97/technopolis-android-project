package com.example.technopark.screens.scheduleritems.row;

import android.view.View;

import com.example.technopark.scheduler.model.SchedulerItem;
import com.example.technopark.screens.common.mvp.MvpView;
import com.example.technopark.screens.common.nav.BackPressedListener;

public interface SchedulerItemsRowMvpView extends MvpView {

    void bindData(SchedulerItem schedulerItem, View.OnClickListener listener);

}
