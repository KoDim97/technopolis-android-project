package com.example.technopolis.images.service;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import com.example.technopolis.images.repo.ImagesRepo;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class ImagesService {
    /**
     * Download image using @Picasso
     * If download put it in repository
     */
    public static void downloadImage(@NonNull final String imageUrl,@NonNull final ImagesRepo imagesRepo){
        boolean download = true;
        Bitmap bitmap = null;
        try {
            bitmap = Picasso.get().load(imageUrl).get();
        } catch (IOException e) {
            download = false;
        }
        if (download)
            imagesRepo.add(imageUrl, bitmap);
    }
}
