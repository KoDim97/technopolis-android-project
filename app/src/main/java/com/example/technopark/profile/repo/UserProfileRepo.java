package com.example.technopark.profile.repo;

import com.example.technopark.profile.model.UserProfile;

        import java.util.List;

public interface UserProfileRepo {

    List<UserProfile> findAll();

    UserProfile findByUserName(String userName);

    UserProfile add(UserProfile userProfile);

    void update(UserProfile userProfile);

    void removeByUserName(String userName);
}
