package com.example.technopolis.profile.repo;

import com.example.technopolis.profile.model.UserProfile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserProfileRepoImpl implements UserProfileRepo {
    private final Map<String, UserProfile> items = new ConcurrentHashMap<>();

    @Override
    public List<UserProfile> findAll() {
        return new ArrayList<>(items.values());
    }

    @Override
    public UserProfile findByUserName(String userName) {
        return items.get(userName);
    }

    @Override
    public UserProfile add(UserProfile userProfile) {
        items.put(userProfile.getUserName(), userProfile);
        return userProfile;
    }

    @Override
    public void update(UserProfile userProfile) {
        items.put(userProfile.getUserName(), userProfile);
    }

    @Override
    public void removeByUserName(String userName) {
        items.remove(userName);
    }
}
