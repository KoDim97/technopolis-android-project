package com.example.technopark.screens.newsitems.row;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.technopark.R;
import com.example.technopark.news.model.NewsItem;
import com.example.technopark.screens.common.mvp.MvpViewBase;
import com.squareup.picasso.Picasso;

public class NewsItemsRowMvpViewImpl extends MvpViewBase implements NewsItemsRowMvpView {

    private final Listener listener;

    private final TextView fullNameTextView;
    private final TextView titleTextView;
    private final TextView blogNameTextView;
    private final TextView datePostingTextView;
    private final TextView commentsCountTextView;
    private final ImageView avatarImage;

    private NewsItem newsItem;

    public NewsItemsRowMvpViewImpl(LayoutInflater layoutInflater, ViewGroup parent,
        NewsItemsRowMvpView.Listener listener) {
        this.listener = listener;
        setRootView(layoutInflater.inflate(R.layout.news_item, parent, false));

        fullNameTextView = findViewById(R.id.news_item__name);
        titleTextView = findViewById(R.id.news_item__title);
        blogNameTextView = findViewById(R.id.news_item__section);
        datePostingTextView = findViewById(R.id.news_item__date);
        commentsCountTextView = findViewById(R.id.news_item__comment_num);
        avatarImage = findViewById(R.id.news_item__image);

        getRootView().setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onNewsItemClicked();
                    }
                });
    }


    @Override
    public void bindData(NewsItem newsItem) {
        this.newsItem = newsItem;
        fullNameTextView.setText(newsItem.getName());
        titleTextView.setText(newsItem.getTitle());
        blogNameTextView.setText(newsItem.getSection());
        datePostingTextView.setText(newsItem.getDate());
        commentsCountTextView.setText(newsItem.getComments_number());
//        avatarImage.setImageDrawable();

<<<<<<< HEAD
//        Picasso.with(getContext()).load(newsItem.getUserpic()).into(avatarImage);
=======
        //Picasso.with(getContext()).load(newsItem.getUserpic()).into(avatarImage);
>>>>>>> 25cb610e13235941589617e4a90670b05619ab41

    }

    public void onNewsItemClicked() {
        listener.onNewsItemClicked(newsItem.getId());
    }
}
