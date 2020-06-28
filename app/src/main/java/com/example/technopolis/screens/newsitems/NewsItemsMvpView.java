package com.example.technopolis.screens.newsitems;

import com.example.technopolis.news.model.NewsItem;
import com.example.technopolis.screens.common.mvp.MvpViewObservable;
import com.example.technopolis.screens.common.nav.BackPressedListener;

import java.util.List;

public interface NewsItemsMvpView extends MvpViewObservable<NewsItemsMvpView.Listener> {

    interface Listener extends BackPressedListener {

        void onNewsItemClicked(String url);

    }

    void showProgress();

    void hideProgress();

    void bindData(List<NewsItem> blogItems);
}
