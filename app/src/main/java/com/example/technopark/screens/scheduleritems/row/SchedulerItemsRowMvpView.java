package com.example.technopark.screens.scheduleritems.row;

import com.example.technopark.scheduler.model.SchedulerItem;
import com.example.technopark.screens.common.mvp.MvpView;

public interface SchedulerItemsRowMvpView extends MvpView {

    void bindData(SchedulerItem schedulerItem);

}
