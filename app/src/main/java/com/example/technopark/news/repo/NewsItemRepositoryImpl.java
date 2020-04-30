package com.example.technopark.news.repo;

import com.example.technopark.news.model.NewsItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NewsItemRepositoryImpl implements NewsItemRepository{

    private final Map<Long, NewsItem> itemsNews = new ConcurrentHashMap<>();

    @Override
    public List<NewsItem> findAll() {
        return new ArrayList<>(itemsNews.values());
    }

    @Override
    public void add(NewsItem newsItem) {
        itemsNews.put(newsItem.getId(), newsItem);
    }

    @Override
    public String findById(long id) {
        return itemsNews.get(id).getUrl();
    }
}
