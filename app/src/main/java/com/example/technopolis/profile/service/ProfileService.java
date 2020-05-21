package com.example.technopolis.profile.service;

import android.graphics.Bitmap;

import com.example.technopolis.api.MailApi;
import com.example.technopolis.api.dto.ProfileDto;
import com.example.technopolis.images.repo.ImagesRepo;
import com.example.technopolis.profile.model.UserProfile;
import com.example.technopolis.profile.repo.UserProfileRepo;
import com.squareup.picasso.Picasso;

import java.io.IOException;

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
            userProfileRepo.add(userProfile);
        }
        return userProfile;
    }

    private UserProfile requestFromServer(String userName) {
        ProfileDto profileDto = api.requestProfileDto(userName);
        UserProfile userProfile;
        if (userName.equals("")) {
            profileDto.setUserName("");
        }

//        Проверяем, ссылка на картинку использует https
//        Если не использует, принудительно меняем http на https
//        Иначе на некоторых устройствах изображение не будет загружаться
        String imageUrl = profileDto.getAvatarUrl();
        if (!imageUrl.equals("null")) {
            if (!imageUrl.contains("https")) {
                imageUrl = imageUrl.replace("http", "https");
            }
            if (imagesRepo.findById(imageUrl) == null) {
                Bitmap bitmap;
                try {
                    bitmap = Picasso.get().load(imageUrl).get();
                } catch (IOException e) {
                    bitmap = null;
                }
                imagesRepo.add(imageUrl, bitmap);
            }
            userProfile = new UserProfile(
                    profileDto.getId(),
                    profileDto.getUserName(),
                    profileDto.getProjectId(),
                    profileDto.getProjectName(),
                    profileDto.getFullName(),
                    profileDto.getGender(),
                    imagesRepo.findById(imageUrl),
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
        } else {
            userProfile = new UserProfile(
                    profileDto.getId(),
                    profileDto.getUserName(),
                    profileDto.getProjectId(),
                    profileDto.getProjectName(),
                    profileDto.getFullName(),
                    profileDto.getGender(),
                    null,
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
        userProfileRepo.add(userProfile);
        return userProfile;
    }


}
