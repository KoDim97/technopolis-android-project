package com.example.technopolis.screens.common.storage;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.technopolis.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.LinkedList;


public class ImageTarget implements Target {
    private Bitmap image;
    private ImageView view;
    private final LinkedList<ImageView> queue;

    ImageTarget() {
        queue = new LinkedList<>();
    }

    void addInQueue(@NonNull final ImageView view) {
        queue.add(view);
    }

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
        while (!queue.isEmpty()) {
            final ImageView imageView = queue.pop();
            imageView.setImageBitmap(bitmap);
        }
        image = bitmap;
    }

    @Override
    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
        if (image != null) {
            if (view != null)
                view.setImageBitmap(image);
            while (!queue.isEmpty()) {
                final ImageView imageView = queue.pop();
                imageView.setImageBitmap(image);
            }
        } else {
            if (view != null) {
                view.setImageResource(R.drawable.img_no_avatar);
                final BitmapDrawable drawable = (BitmapDrawable) view.getDrawable();
                image = drawable.getBitmap();
            }
            while (!queue.isEmpty()) {
                final ImageView imageView = queue.pop();
                imageView.setImageResource(R.drawable.img_no_avatar);
            }

        }
    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {
    }

    @Nullable
    public Bitmap getImage() {
        return image;
    }
}

