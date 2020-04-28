package com.example.technopark.screens.scheduleritems;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.technopark.R;
import com.example.technopark.scheduler.model.SchedulerItem;
import com.example.technopark.screens.common.mvp.MvpViewObservableBase;
import com.example.technopark.screens.common.nav.BackPressedListener;

import java.util.List;

public class SchedulerItemsMvpViewImpl extends MvpViewObservableBase<BackPressedListener>
        implements SchedulerItemsMvpView  {

    private final RecyclerView rvSchedulerItems;
    private final SchedulerItemAdapter schedulerItemAdapter;

    public SchedulerItemsMvpViewImpl(LayoutInflater layoutInflater, ViewGroup parent, Context context) {
        setRootView(layoutInflater.inflate(R.layout.scheduler_view, parent, false));

        schedulerItemAdapter = new SchedulerItemAdapter(layoutInflater);
        rvSchedulerItems = findViewById(R.id.scheduler_recycler_view);
        rvSchedulerItems.setLayoutManager(new LinearLayoutManager(context));
        rvSchedulerItems.setAdapter(schedulerItemAdapter);
    }

    @Override
    public void bindData(List<SchedulerItem> schedulerItems) {
        schedulerItemAdapter.bindData(schedulerItems);
    }
}
