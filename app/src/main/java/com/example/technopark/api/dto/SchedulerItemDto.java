package com.example.technopark.api.dto;

public class SchedulerItemDto {

    private long id;
    private String discipline;
    private String title;
    private String shortTitle;
    private String superShortTitle;
    private String date;
    private String startTime;
    private String endTime;
    private String location;
    //???нужны ли группы??? see api
    private boolean checkedInOpen;
    private boolean attended;
    private String feedbackURL;

    public SchedulerItemDto(long id, String discipline, String title, String shortTitle, String superShortTitle, String date, String startTime, String endTime, String location, boolean checkedInOpen, boolean attended, String feedbackURL) {
        this.id = id;
        this.discipline = discipline;
        this.title = title;
        this.shortTitle = shortTitle;
        this.superShortTitle = superShortTitle;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.checkedInOpen = checkedInOpen;
        this.attended = attended;
        this.feedbackURL = feedbackURL;
    }

    public long getId() {
        return id;
    }

    public String getDiscipline() {
        return discipline;
    }

    public String getTitle() {
        return title;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public String getSuperShortTitle() {
        return superShortTitle;
    }

    public String getDate() {
        return date;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getLocation() {
        return location;
    }

    public boolean isCheckedInOpen() {
        return checkedInOpen;
    }

    public boolean isAttended() {
        return attended;
    }

    public String getFeedbackURL() {
        return feedbackURL;
    }
}
