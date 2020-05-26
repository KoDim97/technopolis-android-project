package com.example.technopolis.save;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;

import com.example.technopolis.App;
import com.example.technopolis.images.model.ImageItem;
import com.example.technopolis.images.repo.ImagesRepo;
import com.example.technopolis.images.repo.ImagesRepoImpl;
import com.example.technopolis.news.model.NewsItem;
import com.example.technopolis.news.repo.NewsItemRepository;
import com.example.technopolis.profile.model.UserProfile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

class SaveImageController {
    private static final String fileNameImage = "Image";
    private static final String fileName = "ImageRepoDisk";

    /**
     * Save information about saved images in "ImageRepoDisk" file
     * number of images || size of image in bytes | image in bytes | number of file ||
     * Save image[i] in "Imagei" file
     */
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

    /**
     * Read information about saved images from "ImageRepoDisk" file
     * Read image[i] from "Imagei" file
     *
     * @return true if read all images else false
     */
    static boolean read(@NonNull final ImagesRepo[] imagesRepo, @NonNull final App app) throws IOException {
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
            if (sizeRead == -1)
                return false;
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

    /**
     * Take only that images that need save (images of profile and news)  from general images's repository and put it in local
     *
     * @return images's repository that need save
     */
    @NonNull
    static ImagesRepo filterImages(@NonNull final ImagesRepo repo, @NonNull final UserProfile profile, @NonNull final NewsItemRepository newsRepo, @NonNull final NewsItemRepository subNewsRepo) {
        final ImagesRepo imagesRepo = new ImagesRepoImpl();
        for (final NewsItem item : newsRepo.findAll()) {
            final String imageUrl = item.getUserpic();
            if (!imageUrl.equals("null") && repo.findById(imageUrl) != null)
                imagesRepo.add(imageUrl, repo.findById(imageUrl));
        }
        for (final NewsItem item : subNewsRepo.findAll()) {
            final String imageUrl = item.getUserpic();
            if (!imageUrl.equals("null") && repo.findById(imageUrl) != null)
                imagesRepo.add(imageUrl, repo.findById(imageUrl));
        }
        final String imageUrl = profile.getAvatarUrl();
        if (!imageUrl.equals("null") && repo.findById(imageUrl) != null)
            imagesRepo.add(imageUrl, repo.findById(imageUrl));
        return imagesRepo;
    }
}
