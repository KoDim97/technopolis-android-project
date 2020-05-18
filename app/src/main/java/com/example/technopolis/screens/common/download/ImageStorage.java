package com.example.technopolis.screens.common.download;

import android.graphics.Bitmap;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.concurrent.ConcurrentHashMap;

public class ImageStorage {
    private final ConcurrentHashMap<String, ImageTarget> images;

    public ImageStorage() {
        images = new ConcurrentHashMap<>();
    }

    public void setImage(@NonNull Bitmap image, @NonNull String imageUrl) {
        final ImageTarget buf = new ImageTarget();
        buf.setImage(image);
        images.put(imageUrl, buf);
    }

    public void downloadImage(@NonNull String imageUrl, @NonNull ImageView view) {
        if (!images.containsKey(imageUrl)) {
            final ImageTarget buf = new ImageTarget();
            buf.setView(view);
            Picasso.get().load(imageUrl).into(buf);
            images.put(imageUrl, buf);
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
