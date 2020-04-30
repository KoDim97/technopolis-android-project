package com.example.technopolis.screens.scheduleritems;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.technopolis.R;
import com.example.technopolis.scheduler.model.SchedulerItem;
import com.example.technopolis.screens.common.mvp.MvpViewObservableBase;
import com.example.technopolis.screens.common.nav.BackPressedListener;
import com.example.technopolis.screens.scheduleritems.stickyHeader.SchedulerItemDecoration;

import java.util.List;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class SchedulerItemsMvpViewImpl extends MvpViewObservableBase<BackPressedListener>
        implements SchedulerItemsMvpView {

    private final RecyclerView rvSchedulerItems;
    private final SchedulerItemAdapter schedulerItemAdapter;
    private final LinearLayoutManager linearLayoutManager;

    public SchedulerItemsMvpViewImpl(LayoutInflater layoutInflater, ViewGroup parent, Context context) {
        setRootView(layoutInflater.inflate(R.layout.scheduler_view, parent, false));

        rvSchedulerItems = findViewById(R.id.scheduler_recycler_view);
        schedulerItemAdapter = new SchedulerItemAdapter(layoutInflater);
        linearLayoutManager = new LinearLayoutManager(context);
        rvSchedulerItems.setLayoutManager(linearLayoutManager);
        rvSchedulerItems.setAdapter(schedulerItemAdapter);
        rvSchedulerItems.addItemDecoration(new SchedulerItemDecoration(schedulerItemAdapter));

        OverScrollDecoratorHelper.setUpOverScroll(rvSchedulerItems, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);


//        decor.setOverScrollStateListener(new IOverScrollStateListener() {
//                                             @Override
//                                             public void onOverScrollStateChange(IOverScrollDecor decor, int oldState, int newState) {
//                                                 switch (newState) {
//                                                     case STATE_BOUNCE_BACK:
//                                                         if (oldState == STATE_DRAG_START_SIDE) {
//                                                             schedulerItemAdapter.updateItems(getSchedulerItems());
//                                                         }
//                                                         break;
//                                                 }
//                                             }
//                                         }
//        );

    }

    @Override
    public void bindData(List<SchedulerItem> schedulerItems, List<View.OnClickListener> listeners, int actualPosition) {
        schedulerItemAdapter.bindData(schedulerItems, listeners);
        linearLayoutManager.scrollToPosition(actualPosition * 2);
    }
}
