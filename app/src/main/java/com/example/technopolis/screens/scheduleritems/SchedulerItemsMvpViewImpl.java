package com.example.technopolis.screens.scheduleritems;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.technopolis.R;
import com.example.technopolis.scheduler.model.SchedulerItem;
import com.example.technopolis.screens.common.mvp.MvpViewObservableBase;
import com.example.technopolis.screens.common.nav.BackPressedListener;
import com.example.technopolis.screens.scheduleritems.stickyHeader.SchedulerItemDecoration;

import java.util.List;

public class SchedulerItemsMvpViewImpl extends MvpViewObservableBase<BackPressedListener>
        implements SchedulerItemsMvpView {

    private final RecyclerView rvSchedulerItems;
    private final SchedulerItemAdapter schedulerItemAdapter;
    private final LinearLayoutManager linearLayoutManager;
    private final ProgressBar progressBar;
    private final SwipeRefreshLayout swipeRefreshLayout;

    public SchedulerItemsMvpViewImpl(LayoutInflater layoutInflater, ViewGroup parent, Context context) {
        setRootView(layoutInflater.inflate(R.layout.scheduler_view, parent, false));

        rvSchedulerItems = (RecyclerView) findViewById(R.id.scheduler_recycler_view);

        progressBar = findViewById(R.id.scheduler_fragment__progress);
        swipeRefreshLayout = findViewById(R.id.swiperefresh_scheduler);

        schedulerItemAdapter = new SchedulerItemAdapter(layoutInflater);
        linearLayoutManager = new LinearLayoutManager(context);
        rvSchedulerItems.setLayoutManager(linearLayoutManager);
        rvSchedulerItems.setAdapter(schedulerItemAdapter);
        rvSchedulerItems.addItemDecoration(new SchedulerItemDecoration(schedulerItemAdapter));

    }

    @Override
    public void bindData(List<SchedulerItem> schedulerItems, List<View.OnClickListener> listeners, List<IsOnlineSupplier> suppliers, int actualPosition) {
        schedulerItemAdapter.bindData(schedulerItems, listeners, suppliers);
        linearLayoutManager.scrollToPosition(actualPosition * 2);
        hideProgress();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        swipeRefreshLayout.setVisibility(View.VISIBLE);
    }
}
