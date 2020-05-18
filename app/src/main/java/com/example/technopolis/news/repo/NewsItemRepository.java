package com.example.technopolis.news.repo;

import com.example.technopolis.news.model.NewsItem;

import java.util.List;

public interface NewsItemRepository {

    List<NewsItem> findAll();

    void add(NewsItem newsItem);

    void clear();
}
