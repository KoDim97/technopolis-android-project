package com.example.technopark.screens.newsitems;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.technopark.news.model.NewsItem;
import com.example.technopark.screens.newsitems.row.NewsItemsRowMvpView;
import com.example.technopark.screens.newsitems.row.NewsItemsRowMvpViewImpl;

import java.util.ArrayList;
import java.util.List;

public class NewsItemsAdapter extends RecyclerView.Adapter<NewsItemsAdapter.NewsItemViewHolder> implements
        NewsItemsRowMvpView.Listener {


    public class NewsItemViewHolder extends RecyclerView.ViewHolder {

        private NewsItemsRowMvpView newsItemsRowMvpView;

        public NewsItemViewHolder(NewsItemsRowMvpView newsItemsRowMvpView) {
            super(newsItemsRowMvpView.getRootView());
            this.newsItemsRowMvpView = newsItemsRowMvpView;
        }
    }

    private final LayoutInflater layoutInflater;
    private final NewsItemsRowMvpView.Listener listener;

    private final List<NewsItem> items = new ArrayList<>();

    public NewsItemsAdapter(LayoutInflater layoutInflater, NewsItemsRowMvpView.Listener listener) {
        this.layoutInflater = layoutInflater;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NewsItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NewsItemsRowMvpView newsItemsRowMvpView = new NewsItemsRowMvpViewImpl(layoutInflater, parent, this);
        return new NewsItemViewHolder(newsItemsRowMvpView);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsItemViewHolder holder, int position) {
        NewsItem newsItem = items.get(position);
        holder.newsItemsRowMvpView.bindData(newsItem);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onNewsItemClicked(long id) {
        listener.onNewsItemClicked(id);
    }

    public void bindData(List<NewsItem> newsItems) {
        items.clear();
        items.addAll(newsItems);
        notifyDataSetChanged();
    }

}
