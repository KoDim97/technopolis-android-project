package com.example.technopolis.save;

import com.example.technopolis.App;
import com.example.technopolis.news.repo.NewsItemRepository;
import com.example.technopolis.profile.model.UserProfile;
import com.example.technopolis.scheduler.repo.SchedulerItemRepo;
import com.example.technopolis.user.model.User;

import java.io.IOException;

public class PauseController {
    private SaveAuthorizationController saveAuthController;
    private App app;

    public PauseController(App app) {
        saveAuthController = new SaveAuthorizationController(app);
        this.app = app;
    }

    public void onPause() {
        if (app.isAuthorized()) {
            saveAuthController.saveAuthorizationInfo(app.provideUser());
            try {
                SaveNewsController.serialize(app.provideNewsItemRepo(),app.provideSubsItemRepo(),app);
                SaveSchedulerController.serialize(app.provideSchedulerItemRepo(), app);
                SaveProfileController.serialize(app.provideUserProfileRepo().findByUserName(app.provideUser().getUsername()),app);
            } catch (IOException e) {
                exit();
            }
        } else exit();
    }

    public void authorized() {
        User[] user = new User[1];
        SchedulerItemRepo[] repoScheduler = new SchedulerItemRepo[1];
        NewsItemRepository[] repoNews = new NewsItemRepository[2];
        UserProfile[] repoProfile = new UserProfile[1];
        if (!saveAuthController.getAuthorized()) {
            app.setAuthorized(false);
            return;
        }
        if (!saveAuthController.getUser(user)) {
            app.setAuthorized(false);
            return;
        }
        try {
            SaveNewsController.read(repoNews,app);
            SaveSchedulerController.read(repoScheduler, app);
//            SaveProfileController.read(repoProfile,app);
        } catch (IOException e) {
            app.setAuthorized(false);
            return;
        }
        app.setUser(user[0]);
        app.setSchedulerItemRepo(repoScheduler[0]);
        app.setSubsItemRepo(repoNews[1]);
        app.setNewsItemRepo(repoNews[0]);
    //    app.provideUserProfileRepo().add(repoProfile[0]);
        app.setAuthorized(true);
    }


    private void exit() {
        saveAuthController.exit();
    }
}
