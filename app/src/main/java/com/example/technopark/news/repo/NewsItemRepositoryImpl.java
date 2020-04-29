package com.example.technopark.news.repo;

import com.example.technopark.news.model.NewsItem;

import java.util.LinkedList;
import java.util.List;

public class NewsItemRepositoryImpl implements NewsItemRepository{

    private final List<NewsItem> itemsNews = new LinkedList<>();


    public List<NewsItem> findAll() {
        return itemsNews;
    }


    public boolean add(NewsItem newsItem) {
        return itemsNews.add(newsItem);
    }


    public void addAll(List<NewsItem> newsList) {
        itemsNews.addAll(newsList);
    }
}
