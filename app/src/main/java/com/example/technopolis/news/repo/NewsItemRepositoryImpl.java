package com.example.technopolis.news.repo;

import com.example.technopolis.news.model.NewsItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class NewsItemRepositoryImpl implements NewsItemRepository {

    private final Map<String, NewsItem> itemsNews = new TreeMap<>((String o1, String o2) -> o2.compareTo(o1));

    @Override
    public List<NewsItem> findAll() {
        return new ArrayList<>(itemsNews.values());
    }

    @Override
    public void add(NewsItem newsItem) {
        itemsNews.put(newsItem.getDate(), newsItem);
    }

    @Override
    public void clear() {
        itemsNews.clear();
    }

}
