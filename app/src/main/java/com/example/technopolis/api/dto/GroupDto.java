package com.example.technopolis.api.dto;

import java.util.List;

public class GroupDto {
    private final long id;
    private final String name;
    private final List<StudentDto> students;

    public GroupDto(long id, String name, List<StudentDto> students) {
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

    public List<StudentDto> getStudents() {
        return students;
    }
}
