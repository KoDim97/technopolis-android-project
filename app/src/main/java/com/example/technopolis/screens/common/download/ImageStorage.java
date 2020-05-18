package com.example.technopolis.screens.common.download;

import android.graphics.Bitmap;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class ImageStorage {
    private final HashMap<String, ImageTarget> images;

    public ImageStorage() {
        images = new HashMap<>();
    }

    public void setImage(@NonNull Bitmap image, @NonNull String imageUrl) {
        ImageTarget buf = new ImageTarget();
        buf.setImage(image);
        images.put(imageUrl, buf);
    }

    public void downloadImage(@NonNull String imageUrl, @NonNull ImageView view) {
        if (!images.containsKey(imageUrl)) {
            ImageTarget buf = new ImageTarget();
            buf.setView(view);
            Picasso.get().load(imageUrl).into(buf);
        } else {
            view.setImageBitmap(getImage(imageUrl));
        }
    }

    @Nullable
    public Bitmap getImage(@NonNull String imageUrl) {
        if (!images.containsKey(imageUrl))
            return null;
        return images.get(imageUrl).getImage();
    }
}
