package com.example.technopolis.api.dto;

public class SchedulerItemCheckInDto {

    private long id;
    private String feedbackURL;

    public long getId() {
        return id;
    }

    public SchedulerItemCheckInDto(long id, String feedbackURL) {
        this.id = id;
        this.feedbackURL = feedbackURL;
    }

    public String getFeedbackURL() {
        return feedbackURL;
    }

}
