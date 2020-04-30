package com.example.technopark.news.repo;

import com.example.technopark.news.model.NewsItem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NewsItemRepositoryImplSubs implements NewsItemRepository {

    private final Map<Long, NewsItem> itemsSubs = new ConcurrentHashMap<>();


    @Override
    public List<NewsItem> findAll() {
        return new ArrayList<>(itemsSubs.values());
    }

    @Override
    public void add(NewsItem newsItem) {
        itemsSubs.put(newsItem.getId(), newsItem);
    }

    @Override
    public String findById(long id) {
        return null;
    }

}
