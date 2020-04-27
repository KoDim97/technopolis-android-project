package com.example.technopark.profile.service;

import com.example.technopark.api.MailApi;
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
        UserProfile blogItemDto = api.requestProfileDto(id);
        BlogItem blogItem = new BlogItem(
                id,
                blogItemDto.getTitle(),
                blogItemDto.getText(),
                blogItemDto.getViewCount(),
                blogItemDto.getUpVotes(),
                blogItemDto.getDownVotes(),
                blogItemDto.getCreated(),
                System.currentTimeMillis()
        );
        blogItemRepo.add(blogItem);
        return blogItem;
    }


}
