package com.example.technopark.screens.scheduleritems;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.technopark.R;
import com.example.technopark.adapter.stickyHeader.SchedulerItemDecoration;
import com.example.technopark.scheduler.model.SchedulerItem;
import com.example.technopark.screens.common.mvp.MvpViewObservableBase;
import com.example.technopark.screens.common.nav.BackPressedListener;

import java.util.List;

import me.everything.android.ui.overscroll.IOverScrollDecor;
import me.everything.android.ui.overscroll.IOverScrollStateListener;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

import static me.everything.android.ui.overscroll.IOverScrollState.STATE_BOUNCE_BACK;
import static me.everything.android.ui.overscroll.IOverScrollState.STATE_DRAG_START_SIDE;

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
        rvSchedulerItems.addItemDecoration(new SchedulerItemDecoration(schedulerItemAdapter));

//        IOverScrollDecor decor = OverScrollDecoratorHelper.setUpOverScroll(rvSchedulerItems, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);
//
//
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
    public void bindData(List<SchedulerItem> schedulerItems, List<View.OnClickListener> listeners) {
        schedulerItemAdapter.bindData(schedulerItems, listeners);
    }
}
