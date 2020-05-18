package com.example.technopolis.screens.common.download;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class ImageStorage {
    private HashMap<String, ImageTarget> images = new HashMap<>();

    public void setImage(Bitmap image, String imageUrl) {
        ImageTarget buf = new ImageTarget();
        buf.setImage(image);
        images.put(imageUrl, buf);
    }

    public void downloadImage(String imageUrl, ImageView view) {
        if (!images.containsKey(imageUrl)) {
            ImageTarget buf = new ImageTarget();
            buf.setView(view);
            Picasso.get().load(imageUrl).into(buf);
        } else {
            view.setImageBitmap(getImage(imageUrl));
        }
    }

    public Bitmap getImage(String imageUrl) {
        if (!images.containsKey(imageUrl))
            return null;
        return images.get(imageUrl).getImage();
    }
}
