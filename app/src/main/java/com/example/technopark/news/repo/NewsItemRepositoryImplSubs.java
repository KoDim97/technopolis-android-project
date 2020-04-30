package com.example.technopark.news.repo;

import com.example.technopark.news.model.NewsItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class NewsItemRepositoryImplSubs implements NewsItemRepository {

    private final SortedMap<String, NewsItem> itemsSubs = new TreeMap<>((String o1, String o2) -> o2.compareTo(o1));


    @Override
    public List<NewsItem> findAll() {
        System.out.println(itemsSubs.values());
        return new ArrayList<>(itemsSubs.values());
    }

    @Override
    public void add(NewsItem newsItem) {
        itemsSubs.put(newsItem.getDate(), newsItem);
    }

}
