package com.example.technopark.screens.newsitems;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.technopark.R;
import com.example.technopark.news.model.NewsItem;
import com.example.technopark.screens.common.mvp.MvpViewObservableBase;
import com.example.technopark.screens.newsitems.row.NewsItemsRowMvpView;

import java.util.List;

public class NewsItemsMvpViewImpl extends MvpViewObservableBase<NewsItemsMvpView.Listener>
        implements NewsItemsMvpView, NewsItemsMvpView.Listener {

    private final RecyclerView rvNewsItems;
    private final NewsItemsAdapter newsItemsAdapter;

    public NewsItemsMvpViewImpl(LayoutInflater layoutInflater, ViewGroup parent, Context context) {
        setRootView(layoutInflater.inflate(R.layout.news_fragment, parent, false));

        newsItemsAdapter = new NewsItemsAdapter(layoutInflater, (NewsItemsRowMvpView.Listener) this);
        rvNewsItems = findViewById(R.id.activity_news__news_list);
        rvNewsItems.setLayoutManager(new LinearLayoutManager(context));
        rvNewsItems.setAdapter(newsItemsAdapter);
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onNewsItemClicked(long id) {
        for (Listener listener : getListeners()) {
            listener.onNewsItemClicked(id);
        }
    }

    @Override
    public void bindData(List<NewsItem> newsItems) {
        newsItemsAdapter.bindData(newsItems);
    }
}
