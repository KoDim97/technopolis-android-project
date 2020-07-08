package com.example.technopolis.api;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.technopolis.scheduler.model.SchedulerItem;
import com.example.technopolis.scheduler.repo.SchedulerItemRepo;
import com.example.technopolis.scheduler.repo.SchedulerItemRepoImpl;
import com.example.technopolis.scheduler.service.SchedulerItemService;
import com.example.technopolis.user.model.User;
import com.example.technopolis.util.MainThreadPoster;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class SchedulerServiceTest {

    private SchedulerItemService schedulerItemService;
    private SchedulerItemRepo schedulerItemRepo;

    //for auth
    private MailApi mailApi;
    private User user;
    private ApiHelper apiHelper;

    @Before
    public void setUp() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        user = new User();
        apiHelper = new ApiHelper(context, new MainThreadPoster());
        mailApi = new MailApiImpl(requestQueue, user, apiHelper);
        schedulerItemRepo = new SchedulerItemRepoImpl();
        schedulerItemService = new SchedulerItemService(schedulerItemRepo, mailApi);
    }

    @Test
    public void checkServiceWorkTest() {
        Assert.assertTrue(schedulerItemRepo.findAll().isEmpty());
        //check that we can get data before authorization
        Assert.assertTrue(schedulerItemService.items().isEmpty());
        MailApiTest.auth(mailApi, user);
        //test that we get user after request auth data
        Assert.assertNotNull(user);

        //check that data were got from the api and were pushed to the repository
        Assert.assertFalse(schedulerItemService.items().isEmpty());
        Assert.assertFalse(schedulerItemRepo.findAll().isEmpty());

        //check service method: request from api
        schedulerItemRepo.updateAll(new ArrayList<SchedulerItem>());
        Assert.assertTrue(schedulerItemRepo.findAll().isEmpty());
        Assert.assertFalse(schedulerItemService.requestFromApi().isEmpty());
        Assert.assertFalse(schedulerItemRepo.findAll().isEmpty());
    }

}
