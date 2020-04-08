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
                        "18:30",
                        "21:30",
                        "онлайн",
                        "Среда, 8 апреля"
                ),
                new SchedulerItem(
                        11233L,
                        "Мобильная разработка",
                        "Проектирование Android приложений",
                        "Смешанное занятие",
                        "18:30",
                        "21:30",
                        "онлайн",
                        "Среда, 9 апреля"
                ),
                new SchedulerItem(
                        12123L,
                        "Использование баз данных",
                        "Введение. Key-Value(Вадим Цесько)",
                        "Л 7",
                        "18:30",
                        "21:30",
                        "онлайн",
                        "Среда, 15 апреля"
                ),
                new SchedulerItem(
                        1L,
                        "Мобильная разработка",
                        "Kotlin",
                        "Смешанное занятие",
                        "18:30",
                        "21:30",
                        "онлайн",
                        "Среда, 16 апреля"
                ),
                new SchedulerItem(
                        333L,
                        "Использование баз данных",
                        "Atomic Broadcast (Дмитрий Щитинин)",
                        "Л 8",
                        "18:30",
                        "21:30",
                        "онлайн",
                        "Среда, 22 апреля"
                ),
                new SchedulerItem(
                        3211L,
                        "Мобильная разработка",
                        "Разработка на IOS - I",
                        "Смешанное занятие",
                        "18:30",
                        "21:30",
                        "онлайн",
                        "Среда, 23 апреля"
                ),
                new SchedulerItem(
                        33873L,
                        "Использование баз данных",
                        "Key-Value. Conflict Resolution (Роман Антипин)",
                        "Л 9",
                        "18:30",
                        "21:30",
                        "онлайн",
                        "Среда, 29 апреля"
                ),
                new SchedulerItem(
                        399211L,
                        "Мобильная разработка",
                        "Разработка на IOS - II",
                        "Смешанное занятие",
                        "18:30",
                        "21:30",
                        "онлайн",
                        "Среда, 30 апреля"
                )
        );
    }
}
