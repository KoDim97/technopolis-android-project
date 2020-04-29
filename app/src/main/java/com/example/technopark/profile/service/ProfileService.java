package com.example.technopark.profile.service;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.technopark.api.MailApi;
import com.example.technopark.api.dto.ProfileDto;
import com.example.technopark.avatars_repo.AvatarItemRepo;
import com.example.technopark.profile.model.UserProfile;
import com.example.technopark.profile.repo.UserProfileRepo;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class ProfileService {
    private final UserProfileRepo userProfileRepo;
    private final MailApi api;
    private final AvatarItemRepo avatarItemRepo;
    private UserProfile currentUserProfile;
    private final Context context;

    public ProfileService(UserProfileRepo userProfileRepo, MailApi api, AvatarItemRepo avatarItemRepo, Context context) {
        this.userProfileRepo = userProfileRepo;
        this.api = api;
        this.avatarItemRepo = avatarItemRepo;
        this.context = context;
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
