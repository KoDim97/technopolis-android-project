package com.example.technopark.group.model;


public class Student {
    private final long id;
    private final String username;
    private final String fullname;
    private final String avatar_url;
    private final boolean online;

    public Student(long id, String username, String fullname, String avatar_url, boolean online) {
        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.avatar_url = avatar_url;
        this.online = online;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFullname() {
        return fullname;
    }

    public String getAvatarUrl() {
        return avatar_url;
    }

    public boolean isOnline() {
        return online;
    }
}
