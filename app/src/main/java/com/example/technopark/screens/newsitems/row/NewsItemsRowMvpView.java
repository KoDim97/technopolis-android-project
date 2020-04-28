package com.example.technopark.screens.newsitems.row;

import com.example.technopark.news.model.NewsItem;
import com.example.technopark.screens.common.mvp.MvpView;

public interface NewsItemsRowMvpView extends MvpView {

    interface Listener {

        void onNewsItemClicked(long id);

    }

    void bindData(NewsItem newsItem);
}
