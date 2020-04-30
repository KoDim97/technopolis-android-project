package com.example.technopark.news.repo;

import com.example.technopark.news.model.NewsItem;

import java.util.List;

public interface NewsItemRepository {

    List<NewsItem> findAll();

    void add(NewsItem newsItem);
}
