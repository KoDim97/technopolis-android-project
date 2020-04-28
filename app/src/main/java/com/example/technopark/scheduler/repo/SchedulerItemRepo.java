package com.example.technopark.scheduler.repo;

import com.example.technopark.api.dto.SchedulerItemCheckInDto;
import com.example.technopark.scheduler.model.SchedulerItem;

import java.util.List;

public interface SchedulerItemRepo {

    SchedulerItem findById(long id);

    List<SchedulerItem> findAll();

    void updateAll(List<SchedulerItem> items);

    List<SchedulerItem> update(SchedulerItem item);

}
