package com.example.technopolis.api;

import com.example.technopolis.api.dto.AuthDto;
import com.example.technopolis.api.dto.GroupDto;
import com.example.technopolis.api.dto.NewsDto;
import com.example.technopolis.api.dto.ProfileDto;
import com.example.technopolis.api.dto.SchedulerItemCheckInDto;
import com.example.technopolis.api.dto.SchedulerItemDto;
import com.example.technopolis.user.model.User;

import java.util.List;
import java.util.Map;

public interface MailApi {


    AuthDto requestAuthDto(String login, String password);

    User getUser();

    ProfileDto requestProfileDto(String username);

    GroupDto requestGroupDto(long id);

    void setProjectUrl(String url);

    String getProjectUrl();

    List<SchedulerItemDto> requestSchedulerItems();

    SchedulerItemCheckInDto checkInSchedulerItem(long id);

    List<NewsDto> requestMainNewsDto(Integer limit, Integer offset);

    List<NewsDto> requestSubscribedNewsDto(Integer limit, Integer offset);

}
