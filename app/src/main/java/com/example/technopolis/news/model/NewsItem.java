package com.example.technopolis.news.model;

import android.graphics.Bitmap;

public class NewsItem {
    private final long id;
    private final String name;
    private final String title;
    private final String username;

    private final String section;

    private final String date;
    private final String userpic;
    private final String comments_number;
    private final String url;
    private final Bitmap bitmap;
    public Bitmap getBitmap() {
        return bitmap;
    }

    public String getUrl() {
        return url;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getSection() {
        return section;
    }

    public String getUsername() {
        return username;
    }

    public String getDate() {
        return date;
    }

    public String getUserpic() {
        return userpic;
    }

    public String getComments_number() {
        return comments_number;
    }


    public NewsItem(long id, String name, String title, String username, String section, String date, String userpic, String comments_number, String url, Bitmap bitmap) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.username = username;
        this.section = section;
        this.date = date;
        this.userpic = userpic;
        this.comments_number = comments_number;
        this.url = url;
        this.bitmap = bitmap;
    }
}
