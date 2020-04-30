package com.example.technopolis.group.model;

import java.util.List;

public class GroupItem {
    private final long id;
    private final String name;
    private final List<Student> students;

    public GroupItem(long id, String name, List<Student> students) {
        this.id = id;
        this.name = name;
        this.students = students;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Student> getStudents() {
        return students;
    }
}
