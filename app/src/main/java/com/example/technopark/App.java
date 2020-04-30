package com.example.technopark;

import android.app.Application;

import com.example.technopark.api.MailApi;
import com.example.technopark.api.MailApiImpl;
import com.example.technopark.scheduler.repo.SchedulerItemRepo;
import com.example.technopark.scheduler.repo.SchedulerItemRepoImpl;
import com.example.technopark.scheduler.service.SchedulerItemService;
import com.example.technopark.util.MainThreadPoster;
import com.example.technopark.util.ThreadPoster;


public class App extends Application {

    private MailApi api;
    private MainThreadPoster mainThreadPoster;
    private SchedulerItemService schedulerItemService;
    private SchedulerItemRepo schedulerItemRepo;

    @Override public void onCreate() {
        super.onCreate();
    }

    private MailApi provideMailApi() {
        if (api == null) {
            api = new MailApiImpl();
        }
        return api;
    }

    public ThreadPoster provideMainThreadPoster() {
        if (mainThreadPoster == null) {
            mainThreadPoster = new MainThreadPoster();
        }
        return mainThreadPoster;
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
}
