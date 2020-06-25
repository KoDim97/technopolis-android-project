package com.example.technopolis.api;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.technopolis.R;
import com.example.technopolis.api.dto.AuthDto;
import com.example.technopolis.api.dto.ProfileDto;
import com.example.technopolis.profile.model.UserGroup;
import com.example.technopolis.user.model.User;
import com.example.technopolis.util.MainThreadPoster;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MailApiTest {

    private MailApi mailApi;
    private ApiHelper apiHelper;
    private User user;

    @Before
    public void setUp() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        user = new User();
        apiHelper = new ApiHelper(context, new MainThreadPoster());
        apiHelper.setMessage(R.string.networkError);
        mailApi = new MailApiImpl(requestQueue, user, apiHelper);
    }

    private void auth(User user) {
        AuthDto authDto = mailApi.requestAuthDto(
                UserData.login,
                UserData.password
        );
        user.setLogin(UserData.login);
        user.setPassword(UserData.password);
        user.setAuth_token(authDto.getAuth_token());
        user.setUser_id(authDto.getId());
        user.setUsername(authDto.getUsername());
    }

    @Test
    public void authorizationTest() {
        Assert.assertNotNull(
                "check that you provide the right login and password to UserData class",
                mailApi.requestAuthDto(
                        UserData.login,
                        UserData.password
                )
        );
        Assert.assertNull(mailApi.requestAuthDto(
                UserData.login,
                "123"
        ));
        Assert.assertNull(mailApi.requestAuthDto(
                "123",
                UserData.password
        ));
    }

    @Test
    public void requestProfileDtoTest() {
        auth(user);
        Assert.assertNotNull(mailApi.requestProfileDto(user.getUsername()));
        apiHelper.clear();

        user.setAuth_token("123");
        Assert.assertNull(mailApi.requestProfileDto(user.getUsername()));
        Assert.assertEquals(R.string.reloadRequest, (int) apiHelper.getMessage());
    }

    @Test
    public void requestGroupDtoTest() {
        auth(user);
        ProfileDto profile = mailApi.requestProfileDto(user.getUsername());
        if (profile.getGroups().size() != 0) {
            UserGroup group = profile.getGroups().get(0);
            Assert.assertNotNull(mailApi.requestGroupDto(group.getId()));
            apiHelper.clear();

            user.setAuth_token("123");
            Assert.assertNull(mailApi.requestGroupDto(group.getId()));
            Assert.assertEquals(R.string.reloadRequest, (int) apiHelper.getMessage());
        }
    }

    @Test
    public void requestMainNewsDtoTest() {
        auth(user);
        Assert.assertNotNull(mailApi.requestMainNewsDto(Integer.MAX_VALUE, 0));
        apiHelper.clear();

        user.setAuth_token("123");
        Assert.assertTrue(mailApi.requestMainNewsDto(Integer.MAX_VALUE, 0).isEmpty());
        Assert.assertEquals(R.string.reloadRequest, (int) apiHelper.getMessage());
    }

    @Test
    public void requestSubscribedNewsDtoTest() {
        auth(user);
        Assert.assertNotNull(mailApi.requestSubscribedNewsDto(Integer.MAX_VALUE, 0));
        apiHelper.clear();

        user.setAuth_token("123");
        Assert.assertTrue(mailApi.requestSubscribedNewsDto(Integer.MAX_VALUE, 0).isEmpty());
        Assert.assertEquals(R.string.reloadRequest, (int) apiHelper.getMessage());
    }

    @Test
    public void requestSchedulerItemsTest() {
        auth(user);
        Assert.assertNotNull(mailApi.requestSchedulerItems());
        apiHelper.clear();

        user.setAuth_token("123");
        Assert.assertTrue(mailApi.requestSchedulerItems().isEmpty());
        Assert.assertEquals(R.string.reloadRequest, (int) apiHelper.getMessage());
    }

}