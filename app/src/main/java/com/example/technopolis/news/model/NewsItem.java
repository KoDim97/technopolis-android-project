package com.example.technopolis.news.model;

import com.example.technopolis.screens.common.download.ImageStorage;

public class NewsItem {
    private final long id;
    private final String name;
    private final String title;
    private final String section;
    private final String date;
    private final String userpic;
    private final String comments_number;
    private final String url;
    private final ImageStorage storage;

    public String getUrl() {
        return url;
    }

    public ImageStorage getStorage() {
        return storage;
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

    public String getDate() {
        return date;
    }

    public String getUserpic() {
        return userpic;
    }

    public String getComments_number() {
        return comments_number;
    }


    public NewsItem(long id, String name, String title, String section, String date, String userpic, String comments_number, String url,ImageStorage storage) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.section = section;
        this.date = date;
        this.userpic = userpic;
        this.comments_number = comments_number;
        this.url = url;
        this.storage=storage;
    }
}
