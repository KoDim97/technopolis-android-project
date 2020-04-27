package com.example.technopark.news.repo;

import com.example.technopark.news.model.NewsItem;

import java.util.List;

public interface NewsItemRepository {

    List<NewsItem> findAll();

    NewsItem findById(long id);

    NewsItem add(NewsItem blogItem);

    NewsItem addAll(List<NewsItem> blogItem);

    void update(NewsItem blogItem);

    void removeById(long id);
}
