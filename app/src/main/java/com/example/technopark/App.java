package com.example.technopark;

import android.app.Application;

import com.android.volley.toolbox.Volley;
import com.example.technopark.api.MailApi;
import com.example.technopark.api.MailApiImpl;
import com.example.technopark.news.repo.NewsItemRepository;
import com.example.technopark.news.repo.NewsItemRepositoryImpl;
import com.example.technopark.news.repo.NewsItemRepositoryImplSubs;
import com.example.technopark.news.service.NewsItemService;
import com.example.technopark.scheduler.repo.SchedulerItemRepo;
import com.example.technopark.scheduler.repo.SchedulerItemRepoImpl;
import com.example.technopark.scheduler.service.SchedulerItemService;
import com.example.technopark.user.model.User;
import com.example.technopark.user.service.AuthService;
import com.example.technopark.util.MainThreadPoster;
import com.example.technopark.util.ThreadPoster;


public class App extends Application {

    private MailApi api;
    private MainThreadPoster mainThreadPoster;

    private User user;
    private AuthService authService;
    private SchedulerItemService schedulerItemService;
    private SchedulerItemRepo schedulerItemRepo;
    private NewsItemRepositoryImpl newsItemRepo;
    private NewsItemRepositoryImplSubs subsItemRepo;
    private NewsItemService newsItemService;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    private MailApi provideMailApi() {
        if (api == null) {
            api = new MailApiImpl(Volley.newRequestQueue(this), provideUser());
        }
        return api;
    }

    public AuthService provideAuthService() {
        if (authService == null) {
            authService = new AuthService(provideMailApi(), provideUser());
        }
        return authService;
    }

    public ThreadPoster provideMainThreadPoster() {
        if (mainThreadPoster == null) {
            mainThreadPoster = new MainThreadPoster();
        }
        return mainThreadPoster;
    }

    public User provideUser() {
        if (user == null) {
            user = new User();
        }
        return user;
    }

    public SchedulerItemRepo provideSchedulerItemRepo() {
        if (schedulerItemRepo == null) {
            schedulerItemRepo = new SchedulerItemRepoImpl();
        }
        return schedulerItemRepo;
    }

    public SchedulerItemService provideSchedulerItemService() {
        if (schedulerItemService == null) {
            schedulerItemService = new SchedulerItemService(provideSchedulerItemRepo(), provideMailApi());
        }
        return schedulerItemService;
    }

    public NewsItemRepository provideNewsItemRepo() {
        if (newsItemRepo == null) {
            newsItemRepo = new NewsItemRepositoryImpl();
        }
        return newsItemRepo;
    }

    public NewsItemRepository provideSubsItemRepo() {
        if (subsItemRepo == null) {
            subsItemRepo = new NewsItemRepositoryImplSubs();
        }
        return subsItemRepo;
    }

    public NewsItemService provideNewsItemService() {
        if (newsItemService == null) {
            newsItemService = new NewsItemService(provideNewsItemRepo(), provideSubsItemRepo(), provideMailApi());
        }
        return newsItemService;
    }
}