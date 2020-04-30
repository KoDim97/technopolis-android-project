package com.example.technopolis.profile.service;

import android.content.Context;

import com.example.technopolis.api.MailApi;
import com.example.technopolis.api.dto.ProfileDto;
import com.example.technopolis.profile.model.UserProfile;
import com.example.technopolis.profile.repo.UserProfileRepo;

public class ProfileService {
    private final UserProfileRepo userProfileRepo;
    private final MailApi api;
    private UserProfile currentUserProfile;
    private final Context context;


    public ProfileService(UserProfileRepo userProfileRepo, MailApi api, Context context) {
        this.userProfileRepo = userProfileRepo;
        this.api = api;
        this.context = context;
    }

    public UserProfile getUserProfile(String userName) {
//        Если передали пустую строку, запрашиваем профиль активного пользователя
        if (userName.equals("")) {
            if (currentUserProfile == null) {
                currentUserProfile = requestFromServer("");
            }
            return currentUserProfile;
        } else {
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
