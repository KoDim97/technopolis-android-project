package com.example.technopolis.screens.common.download;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class ImageStorage implements Target {
    private Bitmap image;
    private ImageView view;

    public void setView(ImageView view) {
        this.view = view;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    @Override
    public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
        if (view != null)
            view.setImageBitmap(bitmap);
        image = bitmap;
    }

    @Override
    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
        view.setImageBitmap(image);
    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {
    }

    public Bitmap getImage() {
        return image;
    }
}
