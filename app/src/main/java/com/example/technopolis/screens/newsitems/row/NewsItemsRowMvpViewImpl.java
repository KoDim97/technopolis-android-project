package com.example.technopolis.screens.newsitems.row;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.technopolis.R;
import com.example.technopolis.news.model.NewsItem;
import com.example.technopolis.screens.common.download.ImageStorage;
import com.example.technopolis.screens.common.mvp.MvpViewBase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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

        getRootView().setOnClickListener(v -> onNewsItemClicked());
    }

    @Override
    public void bindData(NewsItem newsItem, ImageStorage storage) {
        this.newsItem = newsItem;
        fullNameTextView.setText(newsItem.getName());
        titleTextView.setText(newsItem.getTitle());
        blogNameTextView.setText(newsItem.getSection());
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", new Locale("ru", "RU"))
                    .parse(newsItem.getDate());
//            date = Date.from(date.toInstant().plus(Duration.ofHours(3)));
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.HOUR_OF_DAY, 3);
            date = cal.getTime();
            datePostingTextView.setText(new SimpleDateFormat("d MMM yyyy 'г'. 'в' HH:mm", new Locale("ru", "RU"))
                    .format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        commentsCountTextView.setText(newsItem.getComments_number());
        storage.downloadImage(newsItem.getUserpic(), avatarImage);
    }

    public void onNewsItemClicked() {
        listener.onNewsItemClicked(newsItem.getUrl());
    }
}
