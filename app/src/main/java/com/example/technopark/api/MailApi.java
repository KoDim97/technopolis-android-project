package com.example.technopark.api;

import com.example.technopark.api.dto.AuthDto;
import com.example.technopark.api.dto.GroupDto;
import com.example.technopark.api.dto.NewsDto;
import com.example.technopark.api.dto.ProfileDto;
import com.example.technopark.api.dto.ScheduleDto;
import com.example.technopark.api.dto.SchedulerItemCheckInDto;
import com.example.technopark.api.dto.SchedulerItemDto;

import java.util.List;

public interface MailApi {

    AuthDto requestAuthDto(String login, String password);
    ProfileDto requestProfileDto(long id);
    ProfileDto requestMyProfileDto();
    GroupDto requestGroupDto(Integer id);
    NewsDto requestMainNewsDto(Integer limit, Integer offset);
    NewsDto requestSubscribedNewsDto(Integer limit, Integer offset);

    List<SchedulerItemDto> requestSchedulerItems();
    SchedulerItemCheckInDto checkInSchedulerItem(long id);

}
