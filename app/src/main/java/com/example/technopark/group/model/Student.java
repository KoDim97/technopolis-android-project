package com.example.technopark.group.model;

import android.graphics.Bitmap;

public class Student {
    private final long id;
    private final String username;
    private final String fullname;
    private final Bitmap avatar;
    private final boolean online;

    public Student(long id, String username, String fullname, Bitmap avatar, boolean online) {
        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.avatar = avatar;
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

    public Bitmap getAvatar() {
        return avatar;
    }

    public boolean isOnline() {
        return online;
    }
}
