package com.example.technopark;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.technopark.adapter.SchedulerItemAdapter;
import com.example.technopark.dto.SchedulerItem;

import java.util.Arrays;
import java.util.Collection;

public class SchedulerActivity extends AppCompatActivity {

    private RecyclerView schedulerItemsRecyclerView;
    private SchedulerItemAdapter schedulerItemAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scheduler_view);

        initRecyclerView();
        loadUsers();

    }

    private void loadUsers() {
        Collection<SchedulerItem> schedulerItems = getSchedulerItems();
        schedulerItemAdapter.setItems(schedulerItems);
    }

    private void initRecyclerView() {
        schedulerItemsRecyclerView = findViewById(R.id.scheduler_recycler_view);
        schedulerItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        schedulerItemAdapter = new SchedulerItemAdapter();
        schedulerItemsRecyclerView.setAdapter(schedulerItemAdapter);
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
                        false,
                        ""
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
                        false,
                        ""
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
                        false,
                        false,
                        ""
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
                        ""
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
                        ""
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
                        ""
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
                        ""
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
                        ""
                )
        );
    }
}
