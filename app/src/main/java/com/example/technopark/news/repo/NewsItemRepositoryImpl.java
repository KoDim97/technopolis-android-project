package com.example.technopark.news.repo;

import com.example.technopark.news.model.NewsItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NewsItemRepositoryImpl implements NewsItemRepository {

    private final Map<Long, NewsItem> items = new ConcurrentHashMap<>();

    @Override
    public List<NewsItem> findAll() {
        return new ArrayList<>(items.values());
    }

    @Override
    public NewsItem findById(long id) {
        return items.get(id);
    }

    @Override
    public NewsItem add(NewsItem newsItem) {
        return items.put(newsItem.getId(), newsItem);
    }

    @Override
    public void addAll(List<NewsItem> newsList) {
        for (NewsItem newsItem : newsList) {
            items.put(newsItem.getId(), newsItem);
        }
    }

    @Override
    public void update(NewsItem newsItem) {
        items.put(newsItem.getId(), newsItem);
    }

    @Override
    public void removeById(long id) {
        items.remove(id);
    }
}
