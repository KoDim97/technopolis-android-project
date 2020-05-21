package com.example.technopolis.news.service;

import android.graphics.Bitmap;

import com.example.technopolis.api.MailApi;
import com.example.technopolis.api.dto.NewsDto;
import com.example.technopolis.images.repo.ImagesRepo;
import com.example.technopolis.news.model.NewsItem;
import com.example.technopolis.news.repo.NewsItemRepository;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

public class NewsItemService {

    private final NewsItemRepository newsItemRepo;
    private final NewsItemRepository subsItemRepo;
    private final MailApi api;
    private final ImagesRepo imagesRepo;

    public NewsItemService(NewsItemRepository newsItemRepo, NewsItemRepository subsItemRepo, MailApi api, ImagesRepo imagesRepo) {
        this.newsItemRepo = newsItemRepo;
        this.subsItemRepo = subsItemRepo;
        this.api = api;
        this.imagesRepo = imagesRepo;
    }

    public List<NewsItem> getNewsItems() {
        List<NewsItem> newsItemList = newsItemRepo.findAll();
        if (newsItemList.isEmpty()) {
            newsItemList = requestNewsFromServer();
        }

        return newsItemList;
    }

    public List<NewsItem> updateNewsItems() {
        return requestNewsFromServer();
    }

    public void clearNews() {
        newsItemRepo.clear();
    }

    public List<NewsItem> getSubsItems() {
        List<NewsItem> subsItemList = subsItemRepo.findAll();
        if (subsItemList.isEmpty()) {
            subsItemList = requestSubsFromServer();
        }

        return subsItemList;
    }

    public List<NewsItem> updateSubsItems() {
        return requestSubsFromServer();
    }

    public void clearSubs() {
        subsItemRepo.clear();
    }

    private List<NewsItem> requestNewsFromServer() {
        List<NewsDto> newsDtoList = api.requestMainNewsDto(Integer.MAX_VALUE, 0);

        for (final NewsDto newsDto : newsDtoList) {
            String imageUrl = newsDto.getAvatar_url();
            if (!imageUrl.equals("null")) {
                if (!imageUrl.contains("https")) {
                    imageUrl = imageUrl.replace("http", "https");
                }
                if (imagesRepo.findById(imageUrl) == null) {
                    Bitmap bitmap;
                    try {
                        bitmap = Picasso.get().load(imageUrl).get();
                    } catch (IOException e) {
                        bitmap = null;
                    }
                    imagesRepo.add(imageUrl, bitmap);
                }
                newsItemRepo.add(
                        new NewsItem(
                                newsDto.getId(),
                                newsDto.getFullname(),
                                newsDto.getTitle(),
                                newsDto.getBlog(),
                                newsDto.getPublish_date(),
                                imageUrl,
                                newsDto.getComments_count(),
                                newsDto.getPost_url(),
                                imagesRepo.findById(imageUrl)
                        )
                );
            } else {
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
                                null
                        )
                );
            }
        }

        return newsItemRepo.findAll();
    }

    private List<NewsItem> requestSubsFromServer() {
        List<NewsDto> newsDtoList = api.requestSubscribedNewsDto(Integer.MAX_VALUE, 0);
        for (final NewsDto newsDto : newsDtoList) {
            String imageUrl = newsDto.getAvatar_url();
            if (!imageUrl.equals("null")) {
                if (!imageUrl.contains("https")) {
                    imageUrl = imageUrl.replace("http", "https");
                }
                if (imagesRepo.findById(imageUrl) == null) {
                    Bitmap bitmap;
                    try {
                        bitmap = Picasso.get().load(imageUrl).get();
                    } catch (IOException e) {
                        bitmap = null;
                    }
                    imagesRepo.add(imageUrl, bitmap);
                }
                subsItemRepo.add(
                        new NewsItem(
                                newsDto.getId(),
                                newsDto.getFullname(),
                                newsDto.getTitle(),
                                newsDto.getBlog(),
                                newsDto.getPublish_date(),
                                imageUrl,
                                newsDto.getComments_count(),
                                newsDto.getPost_url(),
                                imagesRepo.findById(imageUrl)
                        )
                );
            } else {
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
                                null
                        )
                );
            }
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
