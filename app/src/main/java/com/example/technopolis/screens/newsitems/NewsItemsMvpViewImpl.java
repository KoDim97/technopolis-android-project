package com.example.technopolis.screens.newsitems;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.technopolis.App;
import com.example.technopolis.R;
import com.example.technopolis.log.LogHelper;
import com.example.technopolis.news.model.NewsItem;
import com.example.technopolis.screens.common.mvp.MvpViewObservableBase;
import com.example.technopolis.screens.newsitems.row.NewsItemsRowMvpView;

import java.util.List;

public class NewsItemsMvpViewImpl extends MvpViewObservableBase<NewsItemsMvpView.Listener>
        implements NewsItemsMvpView, NewsItemsRowMvpView.Listener {

    private final RecyclerView rvNewsItems;
    private final NewsItemsAdapter newsItemsAdapter;
    private final ProgressBar progressBar;
    private final SwipeRefreshLayout swipeRefreshLayout;

    private final View view;

    public NewsItemsMvpViewImpl(LayoutInflater layoutInflater, ViewGroup parent, Context context, App app) {
        setRootView(layoutInflater.inflate(R.layout.news_fragment, parent, false));

        progressBar = findViewById(R.id.news_fragment__progress);
        swipeRefreshLayout = findViewById(R.id.swiperefresh_items);

        newsItemsAdapter = new NewsItemsAdapter(layoutInflater, this,app);
        rvNewsItems = findViewById(R.id.activity_news__news_list);
        rvNewsItems.setLayoutManager(new LinearLayoutManager(context));
        rvNewsItems.setAdapter(newsItemsAdapter);
        view = getRootView();

    }

    public View getView() {
        return view;
    }

    RecyclerView getRvNewsItems() {
        return rvNewsItems;
    }

    @Override
    public void onNewsItemClicked(String url) {
        for (Listener listener : getListeners()) {
            listener.onNewsItemClicked(url);
        }
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        swipeRefreshLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void bindData(List<NewsItem> newsItems) {
        newsItemsAdapter.bindData(newsItems);
        hideProgress();
        LogHelper.i(this, "bound data on news fragment");
    }
}
