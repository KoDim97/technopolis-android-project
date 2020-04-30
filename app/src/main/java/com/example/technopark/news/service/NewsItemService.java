package com.example.technopark.news.service;

import com.example.technopark.api.MailApi;
import com.example.technopark.api.dto.NewsDto;
import com.example.technopark.dto.News;
import com.example.technopark.news.model.NewsItem;
import com.example.technopark.news.repo.NewsItemRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
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
        Collections.sort(newsItemList, ((NewsItem o1, NewsItem o2) -> o2.getDate().compareTo(o1.getDate())));
        if (newsItemList.isEmpty()) {
            newsItemList = requestNewsFromServer();
        }
        return newsItemList;
    }

    public List<NewsItem> getSubsItems() {
        List<NewsItem> newsItemList = subsItemRepo.findAll();
        Collections.sort(newsItemList, ((NewsItem o1, NewsItem o2) -> o2.getDate().compareTo(o1.getDate())));
        if (newsItemList.isEmpty()) {
            newsItemList = requestSubsFromServer();
        }
        return newsItemList;
    }

    private List<NewsItem> requestNewsFromServer() {
        List<NewsDto> newsDtoList = api.requestMainNewsDto(Integer.MAX_VALUE, 0);

        for (final NewsDto newsDto : newsDtoList) {
            newsItemRepo.add(
                    new NewsItem(
                            newsDto.getId(),
                            newsDto.getFullname(),
                            newsDto.getTitle(),
                            newsDto.getBlog(),
                            newsDto.getPublish_date(),
                            newsDto.getAvatar_url(),
                            newsDto.getComments_count(),
                            newsDto.getPost_url()
                    )
            );
        }

        List<NewsItem> repoData = newsItemRepo.findAll();
        Collections.sort(repoData, ((NewsItem o1, NewsItem o2) -> o2.getDate().compareTo(o1.getDate())));

        return repoData;
    }

    private List<NewsItem> requestSubsFromServer() {
        List<NewsDto> newsDtoList = api.requestSubscribedNewsDto(Integer.MAX_VALUE, 0);
        for (final NewsDto newsDto : newsDtoList) {
            subsItemRepo.add(
                    new NewsItem(
                            newsDto.getId(),
                            newsDto.getFullname(),
                            newsDto.getTitle(),
                            newsDto.getBlog(),
                            newsDto.getPublish_date(),
                            newsDto.getAvatar_url(),
                            newsDto.getComments_count(),
                            newsDto.getPost_url()
                    )
            );
        }
        List<NewsItem> repoData = subsItemRepo.findAll();
        Collections.sort(repoData, ((NewsItem o1, NewsItem o2) -> o2.getDate().compareTo(o1.getDate())));

        return repoData;
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
