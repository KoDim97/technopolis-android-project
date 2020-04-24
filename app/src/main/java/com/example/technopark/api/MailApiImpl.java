package com.example.technopark.api;

import com.example.technopark.api.dto.AuthDto;
import com.example.technopark.api.dto.GroupDto;
import com.example.technopark.api.dto.NewsDto;
import com.example.technopark.api.dto.ProfileDto;
import com.example.technopark.api.dto.ScheduleDto;

public class MailApiImpl implements MailApi {

    public MailApiImpl() {
    }

    @Override
    public AuthDto requestAuthDto(String login, String password) {
        return null;
    }

    @Override
    public ProfileDto requestProfileDto(String username) {
        return null;
    }

    @Override
    public ProfileDto requestMyProfileDto() {
        return null;
    }

    @Override
    public GroupDto requestGroupDto(Integer id) {
        return null;
    }

    @Override
    public NewsDto requestMainNewsDto(Integer limit, Integer offset) {
        return null;
    }

    @Override
    public NewsDto requestSubscribedNewsDto(Integer limit, Integer offset) {
        return null;
    }

    @Override
    public ScheduleDto requestScheduleDto() {
        return null;
    }

    @Override
    public void scheduleCheckIn(Integer index) {

    }
}
