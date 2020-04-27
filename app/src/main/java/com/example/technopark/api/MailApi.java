package com.example.technopark.api;

import com.example.technopark.api.dto.AuthDto;
import com.example.technopark.api.dto.GroupDto;
import com.example.technopark.api.dto.NewsDto;
import com.example.technopark.api.dto.ProfileDto;
import com.example.technopark.api.dto.ScheduleDto;

import java.util.List;

public interface MailApi {

    AuthDto requestAuthDto(String login, String password);
    ProfileDto requestProfileDto(String username);
    ProfileDto requestMyProfileDto();
    GroupDto requestGroupDto(Integer id);
    List<NewsDto> requestMainNewsDto(Integer limit, Integer offset);
    List<NewsDto> requestSubscribedNewsDto(Integer limit, Integer offset);
    ScheduleDto requestScheduleDto();
    void scheduleCheckIn(Integer index);

}
