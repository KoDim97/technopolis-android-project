package com.example.technopolis.profile.service;

import android.graphics.Bitmap;

import com.example.technopolis.api.MailApi;
import com.example.technopolis.api.dto.ProfileDto;
import com.example.technopolis.images.repo.ImagesRepo;
import com.example.technopolis.images.service.ImagesService;
import com.example.technopolis.profile.model.UserProfile;
import com.example.technopolis.profile.repo.UserProfileRepo;

public class ProfileService {
    private final UserProfileRepo userProfileRepo;
    private final MailApi api;
    private final ImagesRepo imagesRepo;

    public ProfileService(UserProfileRepo userProfileRepo, MailApi api, ImagesRepo imagesRepo) {
        this.userProfileRepo = userProfileRepo;
        this.api = api;
        this.imagesRepo = imagesRepo;
    }

    public UserProfile findByUserName(String userName) {
        UserProfile userProfile = userProfileRepo.findByUserName(userName);
        if (userProfile == null) {
            userProfile = requestFromServer(userName);
            if (userProfile != null) {
                userProfileRepo.add(userProfile);
            }
        }
        return userProfile;
    }

    public void preload() {
        UserProfile userProfile = requestFromServer("");
        if (userProfile != null) {
            userProfileRepo.add(userProfile);
        }
    }

    private UserProfile requestFromServer(String userName) {
        ProfileDto profileDto = api.requestProfileDto(userName);
        if (profileDto == null) {
            return null;
        }

        UserProfile userProfile;
        if (userName.equals("")) {
            profileDto.setUserName("");
        }

//        Проверяем, ссылка на картинку использует https
//        Если не использует, принудительно меняем http на https
//        Иначе на некоторых устройствах изображение не будет загружаться
        String imageUrl = profileDto.getAvatarUrl();
        Bitmap bitmap = null;
        if (!imageUrl.equals("null")) {
            if (!imageUrl.contains("https")) {
                imageUrl = imageUrl.replace("http", "https");
            }
            ImagesService.downloadImage(imageUrl, imagesRepo);
            bitmap = imagesRepo.findById(imageUrl);
        }

        userProfile = new UserProfile(
                profileDto.getId(),
                profileDto.getUserName(),
                profileDto.getProjectId(),
                profileDto.getProjectName(),
                profileDto.getFullName(),
                profileDto.getGender(),
                bitmap,
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

        userProfileRepo.add(userProfile);
        return userProfile;
    }

    public MailApi getApi() {
        return api;
    }
}
