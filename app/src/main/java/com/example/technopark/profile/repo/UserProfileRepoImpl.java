package com.example.technopark.profile.repo;

import com.example.technopark.profile.model.UserProfile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserProfileRepoImpl implements UserProfileRepo {
    private final Map<Long, UserProfile> items = new ConcurrentHashMap<>();

    @Override
    public List<UserProfile> findAll() {
        return new ArrayList<>(items.values());
    }

    @Override
    public UserProfile findById(long id) {
        return items.get(id);
    }

    @Override
    public UserProfile add(UserProfile userProfile) {
        items.put(userProfile.getId(), userProfile);
        return userProfile;
    }

    @Override
    public void update(UserProfile userProfile) {
        items.put(userProfile.getId(), userProfile);
    }

    @Override
    public void removeById(long id) {
        items.remove(id);
    }
}
