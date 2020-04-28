package com.example.technopark.news.model;

public class NewsItem {
    private final long id;
    private final String name;
    private final String title;
    private final String section;
    private final String date;
    private final String userpic;
    private final String comments_number;

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

    public NewsItem(long id, String name, String title, String section, String date, String userpic, String comments_number) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.section = section;
        this.date = date;
        this.userpic = userpic;
        this.comments_number = comments_number;
    }
}
