package com.example.technopark.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.technopark.R;
import com.example.technopark.adapter.SchedulerItemAdapter;
import com.example.technopark.adapter.stickyHeader.SchedulerItemDecoration;
import com.example.technopark.dto.SchedulerItem;

import java.util.Arrays;
import java.util.Collection;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class SchedulerFragment extends Fragment {

    private RecyclerView schedulerItemsRecyclerView;
    private SchedulerItemAdapter schedulerItemAdapter;

    public SchedulerFragment() {
    }

    public static SchedulerFragment newInstance() {
        return new SchedulerFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.scheduler_view, container, false);
        schedulerItemsRecyclerView = view.findViewById(R.id.scheduler_recycler_view);
        initRecyclerView();
        loadUsers();
        return view;
    }

    private void loadUsers() {
        Collection<SchedulerItem> schedulerItems = getSchedulerItems();
        schedulerItemAdapter.setItems(schedulerItems);
    }

    private void initRecyclerView() {
        schedulerItemsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        schedulerItemAdapter = new SchedulerItemAdapter();
        schedulerItemsRecyclerView.setAdapter(schedulerItemAdapter);
        OverScrollDecoratorHelper.setUpOverScroll(schedulerItemsRecyclerView, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);
        schedulerItemsRecyclerView.addItemDecoration(new SchedulerItemDecoration(schedulerItemAdapter));
    }

    private Collection<SchedulerItem> getSchedulerItems() {
        return Arrays.asList(
                new SchedulerItem(
                        123L,
                        "Использование баз данных",
                        "Оптимизация запросов и индексирование",
                        "Л 6",
                        "2020-04-08T18:30:00Z",
                        "2020-04-08T21:30:00Z",
                        "онлайн",
                        "2020-04-08T00:00:00Z",
                        false,
                        true,
                        null
                ),
                new SchedulerItem(
                        11233L,
                        "Мобильная разработка",
                        "Проектирование Android приложений",
                        "Смешанное занятие",
                        "2020-04-09T18:30:00Z",
                        "2020-04-09T21:30:00Z",
                        "онлайн",
                        "2020-04-09T00:00:00Z",
                        false,
                        true,
                        "https://www.google.ru/"
                ),
                new SchedulerItem(
                        12123L,
                        "Использование баз данных",
                        "Введение. Key-Value(Вадим Цесько)",
                        "Л 7",
                        "2020-04-15T18:30:00Z",
                        "2020-04-15T21:30:00Z",
                        "онлайн",
                        "2020-04-15T00:00:00Z",
                        true,
                        false,
                        null
                ),
                new SchedulerItem(
                        1L,
                        "Мобильная разработка",
                        "Kotlin",
                        "Смешанное занятие",
                        "2020-04-16T18:30:00Z",
                        "2020-04-16T21:30:00Z",
                        "онлайн",
                        "2020-04-16T00:00:00Z",
                        false,
                        false,
                        null
                ),
                new SchedulerItem(
                        333L,
                        "Использование баз данных",
                        "Atomic Broadcast (Дмитрий Щитинин)",
                        "Л 8",
                        "2020-04-22T18:30:00Z",
                        "2020-04-22T21:30:00Z",
                        "онлайн",
                        "2020-04-22T00:00:00Z",
                        false,
                        false,
                        null
                ),
                new SchedulerItem(
                        3211L,
                        "Мобильная разработка",
                        "Разработка на IOS - I",
                        "Смешанное занятие",
                        "2020-04-23T18:30:00Z",
                        "2020-04-23T21:30:00Z",
                        "онлайн",
                        "2020-04-23T00:00:00Z",
                        false,
                        false,
                        null
                ),
                new SchedulerItem(
                        33873L,
                        "Использование баз данных",
                        "Key-Value. Conflict Resolution (Роман Антипин)",
                        "Л 9",
                        "2020-04-29T18:30:00Z",
                        "2020-04-29T21:30:00Z",
                        "онлайн",
                        "2020-04-29T00:00:00Z",
                        false,
                        false,
                        null
                ),
                new SchedulerItem(
                        399211L,
                        "Мобильная разработка",
                        "Разработка на IOS - II",
                        "Смешанное занятие",
                        "2020-04-30T18:30:00Z",
                        "2020-04-30T21:30:00Z",
                        "онлайн",
                        "2020-04-30T00:00:00Z",
                        false,
                        false,
                        null
                )
        );
    }
}
