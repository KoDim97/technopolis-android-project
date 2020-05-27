package com.example.technopolis.group.service;

import android.graphics.Bitmap;

import com.example.technopolis.api.MailApi;
import com.example.technopolis.api.dto.AuthDto;
import com.example.technopolis.api.dto.GroupDto;
import com.example.technopolis.api.dto.StudentDto;
import com.example.technopolis.group.model.GroupItem;
import com.example.technopolis.group.model.Student;
import com.example.technopolis.group.repo.GroupItemRepo;
import com.example.technopolis.images.repo.ImagesRepo;
import com.example.technopolis.images.service.ImagesService;
import com.example.technopolis.user.model.User;

import java.util.ArrayList;
import java.util.List;

public class FindGroupItemService {
    private final GroupItemRepo groupItemRepo;
    private final MailApi api;
    private final ImagesRepo imagesRepo;

    public FindGroupItemService(GroupItemRepo groupItemRepo, MailApi api, ImagesRepo imagesRepo) {
        this.groupItemRepo = groupItemRepo;
        this.api = api;
        this.imagesRepo = imagesRepo;
    }

    public void reloadAuthToken() {
        User user = api.getUser();
        AuthDto authDto = api.requestAuthDto(user.getLogin(), user.getPassword());
        user.setAuth_token(authDto.getAuth_token());
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
        if (groupDto != null) {
            List<StudentDto> studentDtoList = groupDto.getStudents();
            List<Student> studentList = new ArrayList<>();

            for (final StudentDto studentDto : studentDtoList) {
                String imageUrl = studentDto.getAvatar();
                Bitmap bitmap = null;
                if (!imageUrl.equals("null")) {
                    if (!imageUrl.contains("https")) {
                        imageUrl = imageUrl.replace("http", "https");
                    }
                    ImagesService.downloadImage(imageUrl, imagesRepo);
                    bitmap = imagesRepo.findById(imageUrl);
                }
                studentList.add(new Student(studentDto.getId(), studentDto.getUsername(),
                        studentDto.getFullname(), bitmap, studentDto.isOnline()));

            }

            GroupItem groupItem = new GroupItem(
                    id,
                    groupDto.getName(),
                    studentList
            );
            groupItemRepo.add(groupItem);
            return groupItem;
        } else {
            return null;
        }
    }
}
