package com.example.technopark.news.service;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.example.technopark.api.MailApi;
import com.example.technopark.api.dto.NewsDto;
import com.example.technopark.news.model.NewsItem;
import com.example.technopark.news.repo.NewsItemRepository;
import com.example.technopark.news.repo.NewsItemRepositoryImpl;
import com.example.technopark.news.repo.NewsItemRepositoryImplSubs;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

public class NewsItemService {

    private final NewsItemRepository newsItemRepo;
    private final NewsItemRepository subsItemRepo;
    private final MailApi api;

    public NewsItemService(NewsItemRepository newsItemRepo, NewsItemRepository subsItemRepo, MailApi api) {
        this.newsItemRepo = newsItemRepo;
        this.subsItemRepo = subsItemRepo;
        this.api = api;
    }

    public List<NewsItem> getNewsItems() {
        List<NewsItem> newsItemList = newsItemRepo.findAll();
        if (newsItemList.isEmpty()) {
            newsItemList = requestNewsFromServer();
        }
        return newsItemList;
    }

    public List<NewsItem> getSubsItems() {
        List<NewsItem> newsItemList = subsItemRepo.findAll();
        if (newsItemList.isEmpty()) {
            newsItemList = requestSubsFromServer();
        }
        return newsItemList;
    }

    private List<NewsItem> requestNewsFromServer() {
        List<NewsDto> newsDtoList = api.requestMainNewsDto(Integer.MAX_VALUE, 0);
        final List<NewsItem> newsList = new ArrayList<>();
        for (final NewsDto newsDto : newsDtoList) {
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

    private List<NewsItem> requestSubsFromServer() {
        List<NewsDto> newsDtoList = api.requestSubscribedNewsDto(Integer.MAX_VALUE, 0);
        final List<NewsItem> newsList = new ArrayList<>();
        for (final NewsDto newsDto : newsDtoList) {
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
        subsItemRepo.addAll(newsList);

        return newsList;
    }

//    private ImageView castLinkToBitmap(String link) {
//        Bitmap bitmap_on_return = null;
//
//        return bitmap_on_return;
//    }

    public List<NewsItem> findAll() {
        return newsItemRepo.findAll();
    }
}
