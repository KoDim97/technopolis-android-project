package com.example.technopark;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.technopark.api.MailApi;
import com.example.technopark.api.MailApiImpl;

import com.example.technopark.group.repo.GroupItemRepo;
import com.example.technopark.group.repo.GroupItemRepoImpl;
import com.example.technopark.group.service.FindGroupItemService;
import com.example.technopark.user.model.User;
import com.example.technopark.user.service.AuthService;
import com.example.technopark.scheduler.repo.SchedulerItemRepo;
import com.example.technopark.scheduler.repo.SchedulerItemRepoImpl;
import com.example.technopark.scheduler.service.SchedulerItemService;
import com.example.technopark.util.MainThreadPoster;
import com.example.technopark.util.ThreadPoster;


public class App extends Application {

    private MailApi api;
    private MainThreadPoster mainThreadPoster;

    private User user;
    private AuthService authService;
    private SchedulerItemService schedulerItemService;
    private SchedulerItemRepo schedulerItemRepo;
    private FindGroupItemService findGroupItemService;
    private GroupItemRepo groupItemRepo;

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

    public GroupItemRepo provideGroupItemRepo() {
        if (groupItemRepo == null) {
            groupItemRepo = new GroupItemRepoImpl();
        }
        return groupItemRepo;
    }

    public FindGroupItemService provideFindGroupItemService() {
        if (findGroupItemService == null) {
            findGroupItemService = new FindGroupItemService(provideGroupItemRepo(), provideMailApi());
        }
        return findGroupItemService;
    }
}