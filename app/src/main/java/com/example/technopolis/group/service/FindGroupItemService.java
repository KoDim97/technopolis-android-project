package com.example.technopolis.group.service;

import com.example.technopolis.api.MailApi;
import com.example.technopolis.api.dto.GroupDto;
import com.example.technopolis.api.dto.StudentDto;
import com.example.technopolis.group.model.GroupItem;
import com.example.technopolis.group.model.Student;
import com.example.technopolis.group.repo.GroupItemRepo;

import java.util.ArrayList;
import java.util.List;

public class FindGroupItemService {
    private final GroupItemRepo groupItemRepo;
    private final MailApi api;

    public FindGroupItemService(GroupItemRepo groupItemRepo, MailApi api) {
        this.groupItemRepo = groupItemRepo;
        this.api = api;
    }

    public GroupItem findById(long id) {
        GroupItem groupItem = groupItemRepo.findById(id);
        if (groupItem == null) {
            groupItem = requestFromServer(id);
        }
        return groupItem;
    }

    private GroupItem requestFromServer(long id) {
        GroupDto groupDto = api.requestGroupDto(id);

        List<StudentDto> studentDtoList = groupDto.getStudents();
        List<Student> studentList = new ArrayList<>();

        for (final StudentDto studentDto : studentDtoList) {
            studentList.add(new Student(studentDto.getId(), studentDto.getUsername(),
                    studentDto.getFullname(), studentDto.getAvatar(), studentDto.isOnline()));
        }

        GroupItem groupItem = new GroupItem(
                id,
                groupDto.getName(),
                studentList
        );
        groupItemRepo.add(groupItem);
        return groupItem;
    }
}
