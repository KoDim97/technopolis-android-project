package com.example.technopark.profile.service;

import com.example.technopark.api.MailApi;
import com.example.technopark.api.dto.ProfileDto;
import com.example.technopark.profile.model.UserProfile;
import com.example.technopark.profile.repo.UserProfileRepo;

public class ProfileService {
    private final UserProfileRepo userProfileRepo;
    private final MailApi api;

    public ProfileService(UserProfileRepo userProfileRepo, MailApi api) {
        this.userProfileRepo = userProfileRepo;
        this.api = api;
    }

    public UserProfile findById(long id) {
        UserProfile userProfile = userProfileRepo.findById(id);
        if (userProfile == null) {
            userProfile = requestFromServer(id);
        }
        return userProfile;
    }

    private UserProfile requestFromServer(long id) {
        ProfileDto profileDto = api.requestProfileDto(id);
        UserProfile userProfile  = new UserProfile(
                id,
                profileDto.getUserName(),
                profileDto.getProjectId(),
                profileDto.getProjectName(),
                profileDto.getFullName(),
                profileDto.getGender(),
                profileDto.getAvatarUrl(),
                profileDto.getMainGroup(),
                profileDto.getBirthDate(),
                profileDto.getAbout(),
                profileDto.getJoinDate(),
                profileDto.getLastSeen(),
                profileDto.getContacts(),
                profileDto.getGroups(),
                profileDto.getAccounts()
        );
        userProfileRepo.add(userProfile);
        return userProfile;
    }
    
}
