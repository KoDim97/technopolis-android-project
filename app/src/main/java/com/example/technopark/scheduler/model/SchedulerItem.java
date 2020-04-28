package com.example.technopark.scheduler.model;

import java.util.Objects;

public class SchedulerItem {

    private long id;
    private String subjectName;
    private String lessonName;
    private String lessonType;
    private String startTime;
    private String endTime;
    private String location;
    private String date;

    private boolean isCheckInOpen;
    private boolean isAttended;
    private String feedbackUrl;

    public SchedulerItem(long id, String subjectName, String lessonName, String lessonType, String startTime, String endTime, String location, String date, boolean isCheckInOpen, boolean isAttended, String feedbackUrl) {
        this.id = id;
        this.subjectName = subjectName;
        this.lessonName = lessonName;
        this.lessonType = lessonType;
        this.startTime = startTime.replace("T", " ").substring(0, startTime.length());
        this.endTime = endTime.replace("T", " ").substring(0, endTime.length());
        this.location = location;
        this.date = date.replace("T", " ").substring(0, date.length());
        this.isCheckInOpen = isCheckInOpen;
        this.isAttended = isAttended;
        this.feedbackUrl = feedbackUrl;
    }

    public long getId() {
        return id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getLessonName() {
        return lessonName;
    }

    public String getLessonType() {
        return lessonType;
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

    public String getDate() {
        return date;
    }

    public boolean isCheckInOpen() {
        return isCheckInOpen;
    }

    public boolean isAttended() {
        return isAttended;
    }

    public String getFeedbackUrl() {
        return feedbackUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SchedulerItem that = (SchedulerItem) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void setAttended(boolean attended) {
        isAttended = attended;
    }

    public void setFeedbackUrl(String feedbackUrl) {
        this.feedbackUrl = feedbackUrl;
    }
}
