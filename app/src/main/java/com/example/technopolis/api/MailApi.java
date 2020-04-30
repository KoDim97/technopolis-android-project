package com.example.technopolis.api;

import com.example.technopolis.api.dto.AuthDto;
import com.example.technopolis.api.dto.GroupDto;
import com.example.technopolis.api.dto.NewsDto;
import com.example.technopolis.api.dto.ProfileDto;
import com.example.technopolis.api.dto.SchedulerItemCheckInDto;
import com.example.technopolis.api.dto.SchedulerItemDto;

import java.util.List;

public interface MailApi {


    AuthDto requestAuthDto(String login, String password);

    ProfileDto requestProfileDto(String username);

    GroupDto requestGroupDto(long id);

    void setProjectUrl(String string);

    List<SchedulerItemDto> requestSchedulerItems();

    SchedulerItemCheckInDto checkInSchedulerItem(long id);

    List<NewsDto> requestMainNewsDto(Integer limit, Integer offset);

    List<NewsDto> requestSubscribedNewsDto(Integer limit, Integer offset);


}
