package com.example.technopolis.api;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.technopolis.api.dto.ProfileDto;
import com.example.technopolis.group.repo.GroupItemRepo;
import com.example.technopolis.group.repo.GroupItemRepoImpl;
import com.example.technopolis.group.service.FindGroupItemService;
import com.example.technopolis.images.repo.ImagesRepo;
import com.example.technopolis.images.repo.ImagesRepoImpl;
import com.example.technopolis.profile.model.UserGroup;
import com.example.technopolis.user.model.User;
import com.example.technopolis.util.MainThreadPoster;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GroupsServiceTest {

    private FindGroupItemService findGroupItemService;
    private GroupItemRepo groupItemRepo;
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

        groupItemRepo = new GroupItemRepoImpl();
        imagesRepo = new ImagesRepoImpl();
        findGroupItemService = new FindGroupItemService(groupItemRepo, mailApi, imagesRepo);
    }

    @Test
    public void checkServiceWorkTest() {
        Assert.assertTrue(imagesRepo.findAll().isEmpty());
        //auth
        MailApiTest.auth(mailApi, user);
        //test that we get user after request auth data
        Assert.assertNotNull(user);

        ProfileDto profileDto = mailApi.requestProfileDto(user.getUsername());
        if (!profileDto.getGroups().isEmpty()) {
            UserGroup userGroup = profileDto.getGroups().get(0);
            Assert.assertNotNull(findGroupItemService.findById(userGroup.getId()));
            Assert.assertNotNull(groupItemRepo.findById(userGroup.getId()));
            Assert.assertFalse(imagesRepo.findAll().isEmpty());
        }
    }

}
