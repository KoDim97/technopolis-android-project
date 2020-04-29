package com.example.technopark.news.repo;

import com.example.technopark.news.model.NewsItem;

import java.util.LinkedList;
import java.util.List;

public class NewsItemRepositoryImplSubs implements NewsItemRepository {

    private final List<NewsItem> itemsSubs = new LinkedList<>();


    public List<NewsItem> findAll() {
        return itemsSubs;
    }


    public boolean add(NewsItem newsItem) {
        return itemsSubs.add(newsItem);
    }


    public void addAll(List<NewsItem> newsList) {
        itemsSubs.addAll(newsList);
    }

}
