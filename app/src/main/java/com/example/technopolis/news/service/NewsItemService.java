package com.example.technopolis.news.service;

import android.graphics.Bitmap;

import com.example.technopolis.api.MailApi;
import com.example.technopolis.api.dto.NewsDto;
import com.example.technopolis.news.model.NewsItem;
import com.example.technopolis.news.repo.NewsItemRepository;
import com.squareup.picasso.Picasso;

import java.io.IOException;
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

    public List<NewsItem> updateNewsItems() {
        newsItemRepo.clear();

        return requestNewsFromServer();
    }

    public List<NewsItem> getSubsItems() {
        List<NewsItem> subsItemList = subsItemRepo.findAll();
        if (subsItemList.isEmpty()) {
            subsItemList = requestSubsFromServer();
        }

        return subsItemList;
    }

    public List<NewsItem> updateSubsItems() {
        subsItemRepo.clear();

        return requestSubsFromServer();
    }

    private List<NewsItem> requestNewsFromServer() {
        List<NewsDto> newsDtoList = api.requestMainNewsDto(Integer.MAX_VALUE, 0);

        for (final NewsDto newsDto : newsDtoList) {
            Bitmap bitmap = null;
            try {
                bitmap = Picasso.get().load(newsDto.getAvatar_url()).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            newsItemRepo.add(
                    new NewsItem(
                            newsDto.getId(),
                            newsDto.getFullname(),
                            newsDto.getTitle(),
                            newsDto.getBlog(),
                            newsDto.getPublish_date(),
                            newsDto.getAvatar_url(),
                            newsDto.getComments_count(),
                            newsDto.getPost_url(),
                            bitmap
                    )
            );
        }

        return newsItemRepo.findAll();
    }

    private List<NewsItem> requestSubsFromServer() {
        List<NewsDto> newsDtoList = api.requestSubscribedNewsDto(Integer.MAX_VALUE, 0);
        for (final NewsDto newsDto : newsDtoList) {
            Bitmap bitmap = null;
            try {
                bitmap = Picasso.get().load(newsDto.getAvatar_url()).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            subsItemRepo.add(
                    new NewsItem(
                            newsDto.getId(),
                            newsDto.getFullname(),
                            newsDto.getTitle(),
                            newsDto.getBlog(),
                            newsDto.getPublish_date(),
                            newsDto.getAvatar_url(),
                            newsDto.getComments_count(),
                            newsDto.getPost_url(),
                            bitmap
                    )
            );
        }

        return subsItemRepo.findAll();
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
