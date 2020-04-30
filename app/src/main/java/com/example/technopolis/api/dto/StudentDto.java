package com.example.technopolis.api.dto;

public class StudentDto {
    private final long id;
    private final String username;
    private final String fullname;
    private final String avatar_url;
    private final boolean online;
    private final double rating;

    public StudentDto(long id, String username, String fullname, String avatar_url, boolean online, double rating) {
        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.avatar_url = avatar_url;
        this.online = online;
        this.rating = rating;
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

    public String getAvatar() {
        return avatar_url;
    }

    public boolean isOnline() {
        return online;
    }

    public double getRating() {
        return rating;
    }
}

