package com.example.technopark.api.dto;

public class NewsDto {
    private long id;
    private String fullname;
    private String title;
    private String blog;
    private String publish_date;
    private String avatar_url;
    private String comments_count;

    public NewsDto(long id, String fullname, String title, String blog, String publish_date, String avatar_url, String comments_count) {
        this.id = id;
        this.fullname = fullname;
        this.title = title;
        this.blog = blog;
        this.publish_date = publish_date;
        this.avatar_url = avatar_url;
        this.comments_count = comments_count;
    }

    public long getId() {
        return id;
    }

    public String getFullname() {
        return fullname;
    }

    public String getTitle() {
        return title;
    }

    public String getBlog() {
        return blog;
    }

    public String getPublish_date() {
        return publish_date;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public String getComments_count() {
        return comments_count;
    }
}
