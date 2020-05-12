package com.example.technopolis.save;

import androidx.annotation.NonNull;

import com.example.technopolis.App;
import com.example.technopolis.group.model.GroupItem;
import com.example.technopolis.news.repo.NewsItemRepository;
import com.example.technopolis.profile.model.UserGroup;
import com.example.technopolis.profile.model.UserProfile;
import com.example.technopolis.scheduler.repo.SchedulerItemRepo;
import com.example.technopolis.user.model.User;

import java.io.IOException;

public class PauseControllerImpl implements PauseController {
    private final SaveAuthorizationController saveAuthController;
    private final App app;

    public PauseControllerImpl(@NonNull App app) {
        saveAuthController = new SaveAuthorizationController(app);
        this.app = app;
    }

    private void saveAuthorized() {
        saveAuthController.saveAuthorizationInfo(app.provideUser(), app.provideUserProfileRepo()
                .findByUserName(app.provideUser().getUsername())
                .getGroups()
                .size());
        try {
            SaveNewsController.serialize(app.provideNewsItemRepo(), app.provideSubsItemRepo(), app);
            SaveSchedulerController.serialize(app.provideSchedulerItemRepo(), app);
            SaveProfileController.serialize(app.provideUserProfileRepo().findByUserName(app.provideUser().getUsername()), app);
            for (final UserGroup item : app.provideUserProfileRepo().findByUserName(app.provideUser().getUsername()).getGroups()) {
                SaveGroupController.serialize(app.provideGroupItemRepo().findById(item.getId()), app);
            }
        } catch (IOException e) {
            exit();
        }
    }

    @Override
    public void onPause() {
        if (app.isAuthorized()) {
            saveAuthorized();
        } else exit();
    }

    @Override
    public void authorized() {
        final User[] user = new User[1];
        final int[] numGroup = new int[1];
        final SchedulerItemRepo[] repoScheduler = new SchedulerItemRepo[1];
        final NewsItemRepository[] repoNews = new NewsItemRepository[2];
        final UserProfile[] repoProfile = new UserProfile[1];

        if (!saveAuthController.getAuthorized()) {
            app.setAuthorized(false);
            return;
        }
        if (!saveAuthController.getUser(user, numGroup)) {
            app.setAuthorized(false);
            return;
        }
        numGroup[0] = numGroup[0] > 0 ? numGroup[0] : 1;
        final GroupItem[][] groupItems = new GroupItem[numGroup[0]][];
        boolean status;
        try {
            status = SaveNewsController.read(repoNews, app);
            status = status && SaveSchedulerController.read(repoScheduler, app);
            status = status && SaveProfileController.read(repoProfile, app);
            for (int i = 0; i < numGroup[0]; i++) {
                groupItems[i] = new GroupItem[1];
                status = status && SaveGroupController.read(groupItems[i], app);
            }
        } catch (IOException e) {
            app.setAuthorized(false);
            return;
        }
        if (!status) {
            app.setAuthorized(false);
            return;
        }
        app.setUser(user[0]);
        app.setSchedulerItemRepo(repoScheduler[0]);
        app.setSubsItemRepo(repoNews[1]);
        app.setNewsItemRepo(repoNews[0]);
        app.provideUserProfileRepo().add(repoProfile[0]);
        for (int i = 0; i < numGroup[0]; i++) {
            app.provideGroupItemRepo().add(groupItems[i][0]);
        }
        app.setAuthorized(true);
    }


    private void exit() {
        saveAuthController.exit();
    }
}
