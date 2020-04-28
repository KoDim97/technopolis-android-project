package com.example.technopark.profile.service;

import com.example.technopark.api.MailApi;
import com.example.technopark.api.dto.ProfileDto;
import com.example.technopark.profile.model.UserProfile;
import com.example.technopark.profile.repo.UserProfileRepo;
import com.example.technopark.user.model.User;

public class ProfileService {
    private final UserProfileRepo userProfileRepo;
    private final MailApi api;
    private UserProfile currentUserProfile;

    public ProfileService(UserProfileRepo userProfileRepo, MailApi api) {
        this.userProfileRepo = userProfileRepo;
        this.api = api;
    }

    public UserProfile getCurrentUserProfile() {
        if (currentUserProfile == null) {
            currentUserProfile = requestFromServer();
        }
        return currentUserProfile;
    }

    public UserProfile findById(long id) {
        UserProfile userProfile = userProfileRepo.findById(id);
        if (userProfile == null) {
            userProfile = requestFromServer(id);
        }
        return userProfile;
    }

    private UserProfile requestFromServer(long id) {
        return null;
    }
    private UserProfile requestFromServer() {
        ProfileDto profileDto = api.requestMyProfileDto();
        UserProfile userProfile;

        return null;
    }

}
