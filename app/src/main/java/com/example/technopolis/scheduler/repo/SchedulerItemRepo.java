package com.example.technopolis.scheduler.repo;

import com.example.technopolis.scheduler.model.SchedulerItem;

import java.util.List;

public interface SchedulerItemRepo {

    SchedulerItem findById(long id);

    List<SchedulerItem> findAll();

    void updateAll(List<SchedulerItem> items);

    List<SchedulerItem> update(SchedulerItem item);

}
