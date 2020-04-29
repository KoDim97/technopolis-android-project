package com.example.technopark.scheduler.service;

import com.example.technopark.api.MailApi;
import com.example.technopark.api.dto.SchedulerItemCheckInDto;
import com.example.technopark.api.dto.SchedulerItemDto;
import com.example.technopark.scheduler.model.SchedulerItem;
import com.example.technopark.scheduler.repo.SchedulerItemRepo;

import java.util.ArrayList;
import java.util.List;

public class SchedulerItemService {

    private final SchedulerItemRepo schedulerItemRepo;
    private final MailApi api;

    public SchedulerItemService(SchedulerItemRepo schedulerItemRepo, MailApi api) {
        this.schedulerItemRepo = schedulerItemRepo;
        this.api = api;
    }

    public List<SchedulerItem> items() {
        List<SchedulerItem> schedulerItems = schedulerItemRepo.findAll();
        if (schedulerItems.isEmpty()) {
            schedulerItems = requestFromApi();
        }
        return schedulerItems;
    }

    private List<SchedulerItem> requestFromApi() {
        List<SchedulerItemDto> schedulerItemsDto = api.requestSchedulerItems();
        List<SchedulerItem> schedulerItems = transformToModelList(schedulerItemsDto);
        schedulerItemRepo.updateAll(schedulerItems);
        return schedulerItems;
    }

    private List<SchedulerItem> transformToModelList(List<SchedulerItemDto> schedulerItemsDto) {
        List<SchedulerItem> schedulerItems = new ArrayList<>();
        for (SchedulerItemDto item : schedulerItemsDto) {
            schedulerItems.add(new SchedulerItem(
                    item.getId(),
                    item.getDiscipline(),
                    item.getTitle(),
                    item.getSuperShortTitle(),
                    item.getStartTime(),
                    item.getEndTime(),
                    item.getLocation(),
                    item.getDate(),
                    item.isCheckedInOpen(),
                    item.isAttended(),
                    item.getFeedbackURL()
            ));
        }
        return schedulerItems;
    }

    public List<SchedulerItem> checkInItem(long id) {
        SchedulerItemCheckInDto schedulerItemCheckInDto = api.checkInSchedulerItem(id);
        SchedulerItem schedulerItemFromCache = schedulerItemRepo.findById(id);
        return schedulerItemRepo.update(schedulerItemFromCache);
    }

    public List<SchedulerItem> findAll() {
        return schedulerItemRepo.findAll();
    }
}
