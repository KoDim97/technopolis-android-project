package com.example.technopolis.screens.newsitems.row;

import com.example.technopolis.news.model.NewsItem;
import com.example.technopolis.screens.common.mvp.MvpView;

public interface NewsItemsRowMvpView extends MvpView {

    interface Listener {

        void onNewsItemClicked(String url);

    }

    void bindData(NewsItem newsItem);
}
