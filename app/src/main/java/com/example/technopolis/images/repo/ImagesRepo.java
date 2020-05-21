package com.example.technopolis.images.repo;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.technopolis.images.model.ImageItem;

import java.util.List;

public interface ImagesRepo {

    @NonNull
    List<ImageItem> findAll();

    void add(final @NonNull String imageUrl, final Bitmap bitmap);

    @Nullable
    Bitmap findById(final @NonNull String imageUrl);

    void clear();
}
