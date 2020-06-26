package com.example.technopolis.api;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.technopolis.images.repo.ImagesRepo;
import com.example.technopolis.images.repo.ImagesRepoImpl;
import com.example.technopolis.news.repo.NewsItemRepository;
import com.example.technopolis.news.repo.NewsItemRepositoryImpl;
import com.example.technopolis.news.repo.NewsItemRepositoryImplSubs;
import com.example.technopolis.news.service.NewsItemService;
import com.example.technopolis.user.model.User;
import com.example.technopolis.util.MainThreadPoster;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class NewsServiceTest {

    private NewsItemService newsItemService;
    private NewsItemRepository newsRepo;
    private NewsItemRepository subsRepo;
    private ImagesRepo imagesRepo;

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
        newsRepo = new NewsItemRepositoryImpl();
        subsRepo = new NewsItemRepositoryImplSubs();
        imagesRepo = new ImagesRepoImpl();
        newsItemService = new NewsItemService(newsRepo, subsRepo, mailApi, imagesRepo);
    }

    @Test
    public void checkServiceWorkTest() {
        Assert.assertTrue(newsItemService.findAll().isEmpty());
        //check that we can get data before authorization
        Assert.assertTrue(newsRepo.findAll().isEmpty());
        Assert.assertTrue(subsRepo.findAll().isEmpty());
        Assert.assertTrue(imagesRepo.findAll().isEmpty());
        MailApiTest.auth(mailApi, user);
        //test that we get user after request auth data
        Assert.assertNotNull(user);

        //check that news were got from the api and were pushed to the repository
        Assert.assertFalse(newsItemService.getNewsItems().isEmpty());
        Assert.assertFalse(newsRepo.findAll().isEmpty());
        Assert.assertFalse(imagesRepo.findAll().isEmpty());

        //check that subs were got from the api and were pushed to the repository
        Assert.assertFalse(newsItemService.getSubsItems().isEmpty());
        Assert.assertFalse(subsRepo.findAll().isEmpty());

        //check service method: request from api
        newsRepo.clear();
        subsRepo.clear();
        imagesRepo.clear();
        Assert.assertTrue(newsRepo.findAll().isEmpty());
        Assert.assertTrue(subsRepo.findAll().isEmpty());
        Assert.assertTrue(imagesRepo.findAll().isEmpty());
        //request data from api again
        Assert.assertFalse(newsItemService.updateNewsItems().isEmpty());
        Assert.assertFalse(newsItemService.updateSubsItems().isEmpty());
        //check that data were pushed again to the repos
        Assert.assertFalse(newsRepo.findAll().isEmpty());
        Assert.assertFalse(subsRepo.findAll().isEmpty());
        Assert.assertFalse(imagesRepo.findAll().isEmpty());
    }

}
