package com.example.technopark.screens.newsitems;

import com.example.technopark.news.model.NewsItem;
import com.example.technopark.news.service.NewsItemService;
import com.example.technopark.screens.common.mvp.MvpPresenter;
import com.example.technopark.screens.common.nav.BackPressDispatcher;
import com.example.technopark.screens.common.nav.ScreenNavigator;
import com.example.technopark.util.ThreadPoster;

import java.util.List;

public class NewsItemsPresenter implements MvpPresenter<NewsItemsMvpView>,
        NewsItemsMvpView.Listener {

    private final ScreenNavigator screenNavigator;
    private final BackPressDispatcher backPressDispatcher;
    private final NewsItemService newsItemService;
    private final ThreadPoster mainThreadPoster;

    private NewsItemsMvpView view;
    private Thread thread;

    public NewsItemsPresenter(ScreenNavigator screenNavigator, BackPressDispatcher backPressDispatcher,
                              NewsItemService newsItemService, ThreadPoster mainThreadPoster) {
        this.screenNavigator = screenNavigator;
        this.backPressDispatcher = backPressDispatcher;
        this.newsItemService = newsItemService;
        this.mainThreadPoster = mainThreadPoster;
    }

    @Override
    public void bindView(NewsItemsMvpView view) {
        this.view = view;
        loadItems();
    }

    private void loadItems() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                final List<NewsItem> newsItems = newsItemService.findAll();
                if (!thread.isInterrupted()) {
                    mainThreadPoster.post(new Runnable() {
                        @Override
                        public void run() {
                            onItemsLoaded(newsItems);
                        }
                    });
                }
            }
        });

        thread.start();
    }

    private void onItemsLoaded(List<NewsItem> newsItems) {
        // prepare to show
        view.bindData(newsItems);
    }

    @Override
    public void onStart() {
        view.registerListener(this);
        backPressDispatcher.registerListener(this);
    }

    @Override
    public void onStop() {
        view.unregisterListener(this);
        backPressDispatcher.unregisterListener(this);
    }

    @Override
    public void onDestroy() {
        thread.interrupt();
        thread = null;
        view = null;
    }

    @Override
    public boolean onBackPressed() {
        screenNavigator.navigateUp();
        return true;
    }

    @Override
    public void onNewsItemClicked(long id) {
        //todo
    }
}
