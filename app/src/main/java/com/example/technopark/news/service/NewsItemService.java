package com.example.technopark.news.service;

import android.widget.ImageView;

import com.example.technopark.api.MailApi;
import com.example.technopark.api.dto.NewsDto;
import com.example.technopark.news.model.NewsItem;
import com.example.technopark.news.repo.NewsItemRepository;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class NewsItemService {

    private final NewsItemRepository newsItemRepo;
    private final MailApi api;

    public NewsItemService(NewsItemRepository newsItemRepo, MailApi api) {
        this.newsItemRepo = newsItemRepo;
        this.api = api;
    }

    public List<NewsItem> getNewsItems() {
        List<NewsItem> newsItemList = newsItemRepo.findAll();
        if (newsItemList == null) {
            newsItemList = requestFromServer();
        }
        return newsItemList;
    }

    private List<NewsItem> requestFromServer() {
        List<NewsDto> newsDtoList = api.requestMainNewsDto(0, 0);
        List<NewsItem> newsList = new ArrayList<>();
        for (NewsDto newsDto : newsDtoList) {
            newsList.add(
                new NewsItem(
                        newsDto.getId(),
                        newsDto.getFullname(),
                        newsDto.getTitle(),
                        newsDto.getBlog(),
                        newsDto.getPublish_date(),
                        newsDto.getAvatar_url(),
                        newsDto.getComments_count()
                )
            );
        }
        newsItemRepo.addAll(newsList);

        return newsList;
    }

    public List<NewsItem> findAll() {
        return newsItemRepo.findAll();
    }
}
