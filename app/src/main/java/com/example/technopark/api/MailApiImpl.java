package com.example.technopark.api;

import com.example.technopark.api.dto.AuthDto;
import com.example.technopark.api.dto.GroupDto;
import com.example.technopark.api.dto.NewsDto;
import com.example.technopark.api.dto.ProfileDto;
import com.example.technopark.api.dto.SchedulerItemCheckInDto;
import com.example.technopark.api.dto.SchedulerItemDto;

import java.util.Arrays;
import java.util.List;

import java.util.Arrays;
import java.util.List;

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
    public List<NewsDto> requestMainNewsDto(Integer limit, Integer offset) {
        return Arrays.asList(
                new NewsDto(
                        2,
                        "Иван Метелёв",
                        "Упражнения после лекции",
                        "Frontend-разработка 2019",
                        "21 октября 2019 г. 12:34",
                        "http://lpark.localhost/media/avatars/gtp/07/01/2121301dde1bec34b962291dad9093ef.jpg",
                        "2"
                ),
                new NewsDto(
                        2,
                        "Филипп Федчин",
                        "День открытых дверей Технополиса",
                        "Мероприятия",
                        "2 декабря 2019 г. 2:56",
                        "http://lpark.localhost/media/avatars/gtp/07/01/2121301dde1bec34b962291dad9093ef.jpg",
                        "0"
                )
        );
    }

    @Override
    public List<NewsDto> requestSubscribedNewsDto(Integer limit, Integer offset) {
        return Arrays.asList(
                new NewsDto(
                        2,
                        "Иван Метелёв",
                        "Упражнения после лекции",
                        "Frontend-разработка 2019",
                        "21 октября 2019 г. 12:34",
                        "http://lpark.localhost/media/avatars/gtp/07/01/2121301dde1bec34b962291dad9093ef.jpg",
                        "2"
                ),
                new NewsDto(
                        2,
                        "Филипп Федчин",
                        "День открытых дверей Технополиса",
                        "Мероприятия",
                        "2 декабря 2019 г. 2:56",
                        "http://lpark.localhost/media/avatars/gtp/07/01/2121301dde1bec34b962291dad9093ef.jpg",
                        "0"
                )
        );
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
