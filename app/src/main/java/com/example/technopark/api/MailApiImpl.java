package com.example.technopark.api;

import com.example.technopark.api.dto.AuthDto;
import com.example.technopark.api.dto.GroupDto;
import com.example.technopark.api.dto.NewsDto;
import com.example.technopark.api.dto.ProfileDto;
import com.example.technopark.api.dto.SchedulerItemCheckInDto;
import com.example.technopark.api.dto.SchedulerItemDto;

import java.util.Arrays;
import java.util.List;

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
=======
public class MailApiImpl implements MailApi {
>>>>>>> parent of 5a101a8... Merge remote-tracking branch 'origin/issue#46' into dev
=======
import java.util.Arrays;
import java.util.List;
>>>>>>> parent of 71fc5a4... Merge branch 'dev' of https://github.com/KoDim97/technopark-android-project into dev
=======
import java.util.Arrays;
import java.util.List;
>>>>>>> parent of 71fc5a4... Merge branch 'dev' of https://github.com/KoDim97/technopark-android-project into dev

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
    public List<SchedulerItemDto> requestSchedulerItems() {
        return Arrays.asList(
                new SchedulerItemDto(
                        123L,
                        "Использование баз данных",
                        "Оптимизация запросов и индексирование",
                        "Лекция 6",
                        "Л 6",
                        "2020-04-08T00:00:00Z",
                        "2020-04-08T18:30:00Z",
                        "2020-04-08T21:30:00Z",
                        "онлайн",
                        false,
                        false,
                        null
                ),
                new SchedulerItemDto(
                        213L,
                        "Использование баз данных",
                        "Оптимизация запросов и индексирование",
                        "Лекция 6",
                        "Л 6",
                        "2020-04-08T00:00:00Z",
                        "2020-04-08T18:30:00Z",
                        "2020-04-08T21:30:00Z",
                        "онлайн",
                        false,
                        false,
                        null
                )
        );
    }

    @Override
    public SchedulerItemCheckInDto checkInSchedulerItem(long id) {
        return new SchedulerItemCheckInDto(123L, "someURL");
    }
}
