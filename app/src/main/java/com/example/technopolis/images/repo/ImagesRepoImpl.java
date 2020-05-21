package com.example.technopolis.images.repo;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.technopolis.images.model.ImageItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImagesRepoImpl implements ImagesRepo {
    private final Map<String, Bitmap> storage;

    public ImagesRepoImpl() {
        storage = new HashMap<>();
    }

    @NonNull
    @Override
    public List<ImageItem> findAll() {
        final List<ImageItem> images = new ArrayList<>();
        for (Map.Entry<String, Bitmap> item : storage.entrySet()) {
            images.add(new ImageItem(item.getKey(), item.getValue()));
        }
        return images;
    }

    @Override
    public void add(final @NonNull String imageUrl, final @NonNull Bitmap bitmap) {
        storage.put(imageUrl, bitmap);
    }

    @Nullable
    @Override
    public Bitmap findById(final @NonNull String imageUrl) {
        return storage.get(imageUrl);
    }

    @Override
    public void clear() {
        storage.clear();
    }
}
