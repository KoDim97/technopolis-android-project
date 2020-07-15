package com.example.technopolis.save;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.technopolis.App;
import com.example.technopolis.images.repo.ImagesRepo;
import com.example.technopolis.news.model.NewsItem;
import com.example.technopolis.news.repo.NewsItemRepository;
import com.example.technopolis.news.repo.NewsItemRepositoryImpl;
import com.example.technopolis.news.repo.NewsItemRepositoryImplSubs;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

class SaveNewsController {
    private final static String fileName = "NewsRepoDisk";
    private final static String fileNameSub = "NewsSubRepoDisk";

    /**
     * Save news's information in "NewsRepoDisk" file
     * Save subscribers's news's information in "NewsSubRepoDisk" file
     */
    static void serialize(@NonNull final NewsItemRepository newsItemRepository, @NonNull final NewsItemRepository newsSubItemRepository, @NonNull final App app) throws IOException {
        serializeNews(newsItemRepository, app, fileName);
        serializeNews(newsSubItemRepository, app, fileNameSub);
    }

    /**
     * Read news's information from "NewsRepoDisk" file
     * Read subscribers's news's information from "NewsSubRepoDisk" file
     *
     * @param newsItemRepositories - repositories of news and subscribers's news (size = 2)
     * @return true if read all news else false
     */
    static boolean read(@NonNull final NewsItemRepository[] newsItemRepositories, @NonNull final App app, @NonNull ImagesRepo repo) throws IOException {
        final NewsItemRepository[] newsItemRepositoriesBuf = new NewsItemRepository[1];
        if (newsItemRepositories.length != 2)
            return false;
        newsItemRepositoriesBuf[0] = new NewsItemRepositoryImpl();
        if (!readNews(newsItemRepositoriesBuf, app, fileName, repo)) {
            return false;
        }
        newsItemRepositories[0] = newsItemRepositoriesBuf[0];
        newsItemRepositoriesBuf[0] = new NewsItemRepositoryImplSubs();
        if (!readNews(newsItemRepositoriesBuf, app, fileNameSub, repo)) {
            return false;
        }
        newsItemRepositories[1] = newsItemRepositoriesBuf[0];
        return true;
    }


    /**
     * Save news's information  in "fileName" file
     * number of news |
     * | size of news's id in bytes | news's id in bytes
     * | size of news's name in bytes | news's name in bytes
     * |size of news's title in bytes | news's title in bytes
     * |size of news's section in bytes | news's section in bytes
     * |size of news's date in bytes | news's date in bytes
     * |size of news's image's url in bytes | news's image's url in bytes
     * |size of news's number of comments in bytes | news's number of comments in bytes
     * |size of news's url in bytes | news's url in bytes
     * |
     */
    private static void serializeNews(@NonNull final NewsItemRepository newsItemRepository, @NonNull final App app, @NonNull final String fileName) throws IOException {
        final FileOutputStream writer = app.getApplicationContext().openFileOutput(fileName, Context.MODE_PRIVATE);
        writer.write(newsItemRepository.findAll().size());
        for (NewsItem item : newsItemRepository.findAll()) {
            writer.write(String.valueOf(item.getId()).getBytes().length);
            writer.write(String.valueOf(item.getId()).getBytes());

            writer.write(item.getName().getBytes().length);
            writer.write(item.getName().getBytes());

            writer.write(item.getTitle().getBytes().length);
            writer.write(item.getTitle().getBytes());

            writer.write(item.getUsername().getBytes().length);
            writer.write(item.getUsername().getBytes());

            writer.write(item.getSection().getBytes().length);
            writer.write(item.getSection().getBytes());

            writer.write(item.getDate().getBytes().length);
            writer.write(item.getDate().getBytes());

            writer.write(item.getUserpic().getBytes().length);
            writer.write(item.getUserpic().getBytes());

            writer.write(item.getComments_number().getBytes().length);
            writer.write(item.getComments_number().getBytes());

            writer.write(item.getUrl().getBytes().length);
            writer.write(item.getUrl().getBytes());
        }
        writer.close();
    }

    private static boolean readNews(@NonNull final NewsItemRepository[] newsItemRepository, @NonNull final App app, @NonNull final String fileName, @NonNull ImagesRepo repo) throws IOException {
        FileInputStream reader;
        try {
            reader = app.getApplicationContext().openFileInput(fileName);
        } catch (FileNotFoundException e) {
            return false;
        }
        int size = reader.read();
        for (int i = 0; i < size; i++) {
            int sizeRead = reader.read();
            if (sizeRead == -1)
                return false;
            byte[] buf = new byte[sizeRead];
            if (reader.read(buf) != sizeRead)
                return false;
            final long id = Long.parseLong(new String(buf));

            sizeRead = reader.read();
            if (sizeRead == -1)
                return false;
            buf = new byte[sizeRead];
            if (reader.read(buf) != sizeRead)
                return false;
            final String name = new String(buf);

            sizeRead = reader.read();
            if (sizeRead == -1)
                return false;
            buf = new byte[sizeRead];
            if (reader.read(buf) != sizeRead)
                return false;
            final String title = new String(buf);

            sizeRead = reader.read();
            if (sizeRead == -1)
                return false;
            buf = new byte[sizeRead];
            if (reader.read(buf) != sizeRead)
                return false;
            final String username = new String(buf);

            sizeRead = reader.read();
            if (sizeRead == -1)
                return false;
            buf = new byte[sizeRead];
            if (reader.read(buf) != sizeRead)
                return false;
            final String section = new String(buf);

            sizeRead = reader.read();
            if (sizeRead == -1)
                return false;
            buf = new byte[sizeRead];
            if (reader.read(buf) != sizeRead)
                return false;
            final String date = new String(buf);

            sizeRead = reader.read();
            if (sizeRead == -1)
                return false;
            buf = new byte[sizeRead];
            if (reader.read(buf) != sizeRead)
                return false;
            final String userpic = new String(buf);

            sizeRead = reader.read();
            if (sizeRead == -1)
                return false;
            buf = new byte[sizeRead];
            if (reader.read(buf) != sizeRead)
                return false;
            final String comments_number = new String(buf);

            sizeRead = reader.read();
            if (sizeRead == -1)
                return false;
            buf = new byte[sizeRead];
            if (reader.read(buf) != sizeRead)
                return false;
            final String url = new String(buf);

            newsItemRepository[0].add(new NewsItem(id, name, title, username, section, date, userpic, comments_number, url, repo.findById(userpic)));
        }
        reader.close();
        return true;
    }

}
