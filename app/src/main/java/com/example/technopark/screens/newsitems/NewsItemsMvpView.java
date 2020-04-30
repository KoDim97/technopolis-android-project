package com.example.technopark.screens.newsitems;

import com.example.technopark.news.model.NewsItem;
import com.example.technopark.screens.common.mvp.MvpViewObservable;
import com.example.technopark.screens.common.nav.BackPressedListener;

import java.util.List;

public interface NewsItemsMvpView extends MvpViewObservable<NewsItemsMvpView.Listener> {

    interface Listener extends BackPressedListener {

        void onNewsItemClicked(String url);

    }

    void bindData(List<NewsItem> blogItems);
}
