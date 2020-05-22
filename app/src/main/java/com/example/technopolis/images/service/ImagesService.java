package com.example.technopolis.images.service;

import android.graphics.Bitmap;

import com.example.technopolis.images.repo.ImagesRepo;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class ImagesService {
    public static void downloadImage(String imageUrl, ImagesRepo imagesRepo){
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
