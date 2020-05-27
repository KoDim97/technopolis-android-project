package com.example.technopolis.screens.scheduleritems.row;

import android.view.View;

import com.example.technopolis.scheduler.model.SchedulerItem;
import com.example.technopolis.screens.common.mvp.MvpView;
import com.example.technopolis.screens.scheduleritems.IsOnlineSupplier;

public interface SchedulerItemsRowMvpView extends MvpView {

    void bindData(SchedulerItem schedulerItem, View.OnClickListener listener, IsOnlineSupplier supplier);

}
