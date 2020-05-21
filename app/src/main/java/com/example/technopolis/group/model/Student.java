package com.example.technopolis.group.model;


import android.graphics.Bitmap;

public class Student {
    private final long id;
    private final String username;
    private final String fullname;
    private final Bitmap bitmap;
    private final boolean online;

    public Student(long id, String username, String fullname, Bitmap bitmap, boolean online) {
        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.bitmap = bitmap;
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
        return bitmap;
    }

    public boolean isOnline() {
        return online;
    }
}
