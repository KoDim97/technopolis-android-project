package com.example.technopolis.news.service;

import android.graphics.Bitmap;

import com.example.technopolis.api.MailApi;
import com.example.technopolis.api.dto.NewsDto;
import com.example.technopolis.images.repo.ImagesRepo;
import com.example.technopolis.images.service.ImagesService;
import com.example.technopolis.news.model.NewsItem;
import com.example.technopolis.news.repo.NewsItemRepository;

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

    public void preload(){
        List<NewsItem> subsItemList = requestSubsFromServer();
        if (!subsItemList.isEmpty()) {
            for(NewsItem item : subsItemList){
                subsItemRepo.add(item);
            }
        }
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
            Bitmap bitmap = null;
            if (!imageUrl.equals("null")) {
                if (!imageUrl.contains("https")) {
                    imageUrl = imageUrl.replace("http", "https");
                }
                ImagesService.downloadImage(imageUrl, imagesRepo);
                bitmap = imagesRepo.findById(imageUrl);
            }
            newsItemRepo.add(
                    new NewsItem(
                            newsDto.getId(),
                            newsDto.getFullname(),
                            newsDto.getTitle(),
                            newsDto.getUsername(), newsDto.getBlog(),
                            newsDto.getPublish_date(),
                            imageUrl,
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
            String imageUrl = newsDto.getAvatar_url();
            Bitmap bitmap = null;
            if (!imageUrl.equals("null")) {
                if (!imageUrl.contains("https")) {
                    imageUrl = imageUrl.replace("http", "https");
                }
                ImagesService.downloadImage(imageUrl, imagesRepo);
                bitmap = imagesRepo.findById(imageUrl);
            }
            subsItemRepo.add(
                    new NewsItem(
                            newsDto.getId(),
                            newsDto.getFullname(),
                            newsDto.getTitle(),
                            newsDto.getUsername(), newsDto.getBlog(),
                            newsDto.getPublish_date(),
                            imageUrl,
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


    public MailApi getApi() {
        return api;
    }

    public List<NewsItem> findAll() {
        return newsItemRepo.findAll();
    }
}
