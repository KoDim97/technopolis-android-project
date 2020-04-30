package com.example.technopolis.profile.repo;

import com.example.technopolis.profile.model.UserProfile;

import java.util.List;

public interface UserProfileRepo {

    List<UserProfile> findAll();

    UserProfile findByUserName(String userName);

    UserProfile add(UserProfile userProfile);

    void update(UserProfile userProfile);

    void removeByUserName(String userName);
}
