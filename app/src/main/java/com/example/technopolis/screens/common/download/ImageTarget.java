package com.example.technopolis.screens.common.download;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;


public class ImageTarget implements Target {
    private Bitmap image;
    private ImageView view;

    public void setView(@NonNull ImageView view) {
        this.view = view;
    }

    void setImage(@NonNull Bitmap image) {
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

    @Nullable
    Bitmap getImage() {
        return image;
    }
}

