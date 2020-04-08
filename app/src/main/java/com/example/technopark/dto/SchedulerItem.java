package com.example.technopark.dto;

public class SchedulerItem {

    private long id;
    private String subjectName;
    private String lessonName;
    private String lessonType;
    private String startTime;
    private String endTime;
    private String location;
    private String date;

    public SchedulerItem(long id, String subjectName, String lessonName, String lessonType, String startTime, String endTime, String location, String date) {
        this.id = id;
        this.subjectName = subjectName;
        this.lessonName = lessonName;
        this.lessonType = lessonType;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.date = date;
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
}
