package com.example.technopark.profile.repo;

import com.example.technopark.profile.model.UserProfile;

        import java.util.List;

public interface UserProfileRepo {

    List<UserProfile> findAll();

    UserProfile findById(long id);

    UserProfile add(UserProfile userProfile);

    void update(UserProfile userProfile);

    void removeById(long id);
}
