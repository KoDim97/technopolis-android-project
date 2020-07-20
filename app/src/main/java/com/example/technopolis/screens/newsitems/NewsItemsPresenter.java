package com.example.technopolis.screens.newsitems;

import android.content.Context;
import android.widget.Toast;

import com.example.technopolis.App;
import com.example.technopolis.BaseActivity;
import com.example.technopolis.R;
import com.example.technopolis.api.ApiHelper;
import com.example.technopolis.news.model.NewsItem;
import com.example.technopolis.news.service.NewsItemService;
import com.example.technopolis.screens.common.mvp.MvpPresenter;
import com.example.technopolis.screens.common.nav.BackPressDispatcher;
import com.example.technopolis.screens.common.nav.ScreenNavigator;
import com.example.technopolis.util.ThreadPoster;

import java.util.List;


public class NewsItemsPresenter implements MvpPresenter<NewsItemsMvpView>, NewsItemsMvpView.Listener {

    private final NewsItemService newsItemService;
    private final ThreadPoster mainThreadPoster;
    private final Context context;
    private final ApiHelper apiHelper;
    private ScreenNavigator screenNavigator;
    private BackPressDispatcher backPressDispatcher;
    private BaseActivity activity;

    private NewsItemsMvpView view;

    public Thread getThread() {
        return thread;
    }

    private Thread thread;

    public NewsItemsPresenter(ScreenNavigator screenNavigator, BaseActivity activity,
                              NewsItemService newsItemService, ThreadPoster mainThreadPoster, Context context, ApiHelper apiHelper) {
        this.screenNavigator = screenNavigator;
        this.backPressDispatcher = activity;
        this.newsItemService = newsItemService;
        this.mainThreadPoster = mainThreadPoster;
        this.context = context;
        this.activity = activity;
        this.apiHelper = apiHelper;
    }

    @Override
    public void bindView(NewsItemsMvpView view) {
        this.view = view;
        view.showProgress();
    }

    @Override
    public void onTurnScreen(ScreenNavigator screenNavigator, BaseActivity activity) {
        this.screenNavigator = screenNavigator;
        this.activity = activity;
        this.backPressDispatcher = activity;
    }

    public void updateDataNews() {
        thread = new Thread(() -> {
            final List<NewsItem> newsItems = newsItemService.updateNewsItems();

            if (!apiHelper.showMessageIfExist(newsItemService.getApi(), screenNavigator, this::updateDataNews)) {
                newsItemService.clearNews();
                if (thread != null && !thread.isInterrupted()) {
                    mainThreadPoster.post(() -> {
                        onItemsLoaded(newsItems);
                        NewsItemsFragment.handler.sendMessage(NewsItemsFragment.handler.obtainMessage());
                    });
                }
            } else {
                NewsItemsFragment.handler.sendMessage(NewsItemsFragment.handler.obtainMessage());
            }
        });

        thread.start();
    }

    public void newsItems() {
        thread = new Thread(() -> {
            final List<NewsItem> newsItems = newsItemService.getNewsItems();
            if (!apiHelper.showMessageIfExist(newsItemService.getApi(), screenNavigator, this::newsItems)) {
                if (thread != null && !thread.isInterrupted()) {
                    mainThreadPoster.post(() -> {
                        onItemsLoaded(newsItems);
                    });
                }
            }
        });

        thread.start();
    }

    public void updateDataSubs() {
        thread = new Thread(() -> {
            final List<NewsItem> newsItems = newsItemService.updateSubsItems();

            if (!apiHelper.showMessageIfExist(newsItemService.getApi(), screenNavigator, this::updateDataSubs)) {
                newsItemService.clearSubs();
                if (thread != null && !thread.isInterrupted()) {
                    mainThreadPoster.post(() -> {
                        onItemsLoaded(newsItems);
                        NewsItemsFragment.handler.sendMessage(NewsItemsFragment.handler.obtainMessage());
                    });
                }
            } else {
                NewsItemsFragment.handler.sendMessage(NewsItemsFragment.handler.obtainMessage());
            }
        });

        thread.start();
    }

    public void subsItems() {
        thread = new Thread(() -> {
            final List<NewsItem> newsItems = newsItemService.getSubsItems();
            if (!apiHelper.showMessageIfExist(newsItemService.getApi(), screenNavigator, this::subsItems)) {
                if (thread != null && !thread.isInterrupted()) {
                    mainThreadPoster.post(() -> onItemsLoaded(newsItems));
                }
            }
        });

        thread.start();
    }


    private void onItemsLoaded(List<NewsItem> newsItems) {
        // prepare to show
        if (view != null) {
            view.bindData(newsItems);
        }
    }

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
        if (thread != null) {
            thread.interrupt();
        }
        thread = null;
        view = null;
    }

    @Override
    public boolean onBackPressed() {
        return screenNavigator.navigateUp();
    }

    @Override
    public void onNewsItemClicked(String url) {
        App app = (App) activity.getApplication();

        if (app.isAuthorized()) {
            if (!apiHelper.isOnline()) {
                activity.runOnUiThread(() -> Toast.makeText(activity, R.string.networkError, Toast.LENGTH_SHORT).show());
            } else {
                screenNavigator.toFeedBack(url);
            }

        }
    }

    @Override
    public void onAvatarClicked(String username) {
        App app = (App) activity.getApplication();

        if (app.isAuthorized()) {
            if (!apiHelper.isOnline()) {
                activity.runOnUiThread(() -> Toast.makeText(activity, R.string.networkError, Toast.LENGTH_SHORT).show());
            } else {
                screenNavigator.toProfile(username);
            }

        }
    }
}
