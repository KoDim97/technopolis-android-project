package com.example.technopolis.scheduler.repo;

import com.example.technopolis.scheduler.model.SchedulerItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SchedulerItemRepoImpl implements SchedulerItemRepo {

    private final Map<Long, SchedulerItem> items = new ConcurrentHashMap<>();

    @Override
    public SchedulerItem findById(long id) {
        return items.get(id);
    }

    @Override
    public List<SchedulerItem> findAll() {
        return new ArrayList<>(items.values());
    }

    @Override
    public void updateAll(List<SchedulerItem> newItems) {
        items.clear();
        for (SchedulerItem item : newItems) {
            items.put(item.getId(), item);
        }
    }

    @Override
    public List<SchedulerItem> update(SchedulerItem item) {
        items.put(item.getId(), item);
        return new ArrayList<>(items.values());
    }
}
