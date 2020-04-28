package com.example.technopark.avatars_repo;

import android.graphics.Bitmap;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AvatarItemRepoImpl implements AvatarItemRepo {

    private final Map<Long, Bitmap> items = new ConcurrentHashMap<>();
    @Override
    public Bitmap findById(long id) {
        return items.get(id);
    }

    @Override
    public void add(Bitmap avatar, long id) {
        items.put(id, avatar);
    }

    @Override
    public void update(Bitmap avatar, long id) {
        items.put(id, avatar);
    }

    @Override
    public void removeById(long id) {
        items.remove(id);
    }
}
