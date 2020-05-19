package com.example.technopolis.scheduler.service;

import com.example.technopolis.api.MailApi;
import com.example.technopolis.api.dto.SchedulerItemCheckInDto;
import com.example.technopolis.api.dto.SchedulerItemDto;
import com.example.technopolis.scheduler.model.SchedulerItem;
import com.example.technopolis.scheduler.repo.SchedulerItemRepo;

import java.util.ArrayList;
import java.util.Collections;
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
        Collections.sort(schedulerItems, (SchedulerItem i1, SchedulerItem i2) -> i1.getDate().compareTo(i2.getDate()));
        return schedulerItems;
    }

    public List<SchedulerItem> requestFromApi() {
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
        schedulerItemFromCache.setFeedbackUrl(schedulerItemCheckInDto.getFeedbackURL());
        return schedulerItemRepo.update(schedulerItemFromCache);
    }

    public List<SchedulerItem> findAll() {
        return schedulerItemRepo.findAll();
    }
}
