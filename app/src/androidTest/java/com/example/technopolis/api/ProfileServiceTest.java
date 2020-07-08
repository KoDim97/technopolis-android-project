package com.example.technopolis.api;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.technopolis.images.repo.ImagesRepo;
import com.example.technopolis.images.repo.ImagesRepoImpl;
import com.example.technopolis.profile.repo.UserProfileRepo;
import com.example.technopolis.profile.repo.UserProfileRepoImpl;
import com.example.technopolis.profile.service.ProfileService;
import com.example.technopolis.user.model.User;
import com.example.technopolis.util.MainThreadPoster;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ProfileServiceTest {

    private ProfileService profileService;
    private UserProfileRepo userProfileRepo;
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

        userProfileRepo = new UserProfileRepoImpl();
        imagesRepo = new ImagesRepoImpl();
        profileService = new ProfileService(userProfileRepo, mailApi, imagesRepo);
    }

    @Test
    public void checkServiceWorkTest() {
        //check that we can get data before authorization
        Assert.assertTrue(userProfileRepo.findAll().isEmpty());
        Assert.assertTrue(imagesRepo.findAll().isEmpty());
        MailApiTest.auth(mailApi, user);
        //test that we get user after request auth data
        Assert.assertNotNull(user);

        //check that news were got from the api and were pushed to the repository
        Assert.assertNotNull(profileService.findByUserName(user.getUsername()));
        Assert.assertNotNull(userProfileRepo.findByUserName(user.getUsername()));
        Assert.assertFalse(userProfileRepo.findAll().isEmpty());
        if (userProfileRepo.findByUserName(user.getUsername()).getAvatar() != null) {
            Assert.assertFalse(imagesRepo.findAll().isEmpty());
        }

        //check service method: request from api
        userProfileRepo.removeByUserName(user.getUsername());
        imagesRepo.clear();
        Assert.assertTrue(userProfileRepo.findAll().isEmpty());
        Assert.assertTrue(imagesRepo.findAll().isEmpty());
        //check that news were got from the api and were pushed to the repository
        Assert.assertNotNull(profileService.findByUserName(user.getUsername()));
        Assert.assertNotNull(userProfileRepo.findByUserName(user.getUsername()));
        Assert.assertFalse(userProfileRepo.findAll().isEmpty());
        if (userProfileRepo.findByUserName(user.getUsername()).getAvatar() != null) {
            Assert.assertFalse(imagesRepo.findAll().isEmpty());
        }
    }

}
