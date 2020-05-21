package com.example.technopolis.images.model;

import android.graphics.Bitmap;

public class ImageItem {
    private final String imageUrl;
    private final Bitmap image;

    public ImageItem(String imageUrl, Bitmap image) {
        this.imageUrl = imageUrl;
        this.image = image;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
