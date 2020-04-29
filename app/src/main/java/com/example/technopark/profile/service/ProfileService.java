package com.example.technopark.profile.service;

import android.content.Context;

import com.example.technopark.api.MailApi;
import com.example.technopark.api.dto.ProfileDto;
import com.example.technopark.profile.model.UserProfile;
import com.example.technopark.profile.repo.UserProfileRepo;

public class ProfileService {
    private final UserProfileRepo userProfileRepo;
    private final MailApi api;
    private UserProfile currentUserProfile;
    private final Context context;
    private final static String CURRENT_USER = "current_user";


    public ProfileService(UserProfileRepo userProfileRepo, MailApi api, Context context) {
        this.userProfileRepo = userProfileRepo;
        this.api = api;
        this.context = context;
    }

    public UserProfile getUserProfile(String userName) {
        if (userName == CURRENT_USER) {
            if (currentUserProfile == null) {
                currentUserProfile = requestFromServer("");
            }
            return currentUserProfile;
        }
        else {
            return findByUserName(userName);
        }

    }

    public UserProfile findByUserName(String userName) {
        UserProfile userProfile = userProfileRepo.findByUserName(userName);
        if (userProfile == null) {
            userProfile = requestFromServer(userName);
        }
        return userProfile;
    }

    private UserProfile requestFromServer(String userName) {
        ProfileDto profileDto = api.requestProfileDto(userName);

//        Проверяем, ссылка на картинку использует https
//        Если не использует, принудительно меняем http на https
//        Иначе на некоторых устройствах изображение не будет загружаться
        String imageUrl = profileDto.getAvatarUrl();
        if (!imageUrl.contains("https")) {
            imageUrl = imageUrl.replace("http", "https");
        }

        return new UserProfile(
                profileDto.getId(),
                profileDto.getUserName(),
                profileDto.getProjectId(),
                profileDto.getProjectName(),
                profileDto.getFullName(),
                profileDto.getGender(),
                imageUrl,
                profileDto.getMainGroup(),
                profileDto.getBirthDate(),
                profileDto.getAbout(),
                profileDto.getJoinDate(),
                profileDto.getLastSeen(),
                profileDto.getContacts(),
                profileDto.getGroups(),
                profileDto.getAccounts()
        );
    }


}
