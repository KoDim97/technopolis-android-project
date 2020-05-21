package com.example.technopolis.save;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;

import com.example.technopolis.App;
import com.example.technopolis.images.model.ImageItem;
import com.example.technopolis.images.repo.ImagesRepo;
import com.example.technopolis.images.repo.ImagesRepoImpl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

class SaveImageController {
    private static final String fileNameImage = "Image";
    private static final String fileName = "ImageRepoDisk";

    static void serialize(@NonNull final ImagesRepo imagesRepo, @NonNull final App app) throws IOException {
        final FileOutputStream writer = app.getApplicationContext().openFileOutput(fileName, Context.MODE_PRIVATE);
        int i = 0;
        writer.write(imagesRepo.findAll().size());
        for (ImageItem item : imagesRepo.findAll()) {
            writer.write(item.getImageUrl().getBytes().length);
            writer.write(item.getImageUrl().getBytes());
            writer.write(i);
            final String name = fileNameImage + i;
            final FileOutputStream writerImage = app.getApplicationContext().openFileOutput(name, Context.MODE_PRIVATE);
            final Bitmap bitmap = item.getImage();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, writerImage);
            writerImage.close();
            i++;
        }
        writer.close();
    }

    static boolean read(@NonNull final ImagesRepo[] imagesRepo,@NonNull final App app) throws IOException {
        FileInputStream reader;
        ImagesRepo imageStorage = new ImagesRepoImpl();
        try {
            reader = app.getApplicationContext().openFileInput(fileName);
        } catch (FileNotFoundException e) {
            return false;
        }
        int size = reader.read();
        for (int i = 0; i < size; i++) {
            final int sizeRead = reader.read();
            final byte[] buf = new byte[sizeRead];
            if (reader.read(buf) != sizeRead)
                return false;
            final String imageUrl = new String(buf);

            final int id = reader.read();

            FileInputStream readerImage;
            try {
                readerImage = app.getApplicationContext().openFileInput(fileNameImage + id);
            } catch (FileNotFoundException e) {
                return false;
            }
            final Bitmap bitmap = BitmapFactory.decodeStream(readerImage);
            readerImage.close();
            imageStorage.add(imageUrl, bitmap);
        }
        reader.close();
        imagesRepo[0] = imageStorage;
        return true;
    }
}
