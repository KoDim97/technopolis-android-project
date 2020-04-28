package com.example.technopark.group.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.example.technopark.api.MailApi;
import com.example.technopark.api.dto.GroupDto;
import com.example.technopark.api.dto.StudentDto;
import com.example.technopark.avatars_repo.AvatarItemRepo;
import com.example.technopark.group.model.GroupItem;
import com.example.technopark.group.model.Student;
import com.example.technopark.group.repo.GroupItemRepo;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

public class FindGroupItemService {
    private final GroupItemRepo groupItemRepo;
    private final AvatarItemRepo avatarItemRepo;
    private final MailApi api;
    private final Context context;

    public FindGroupItemService(Context context, GroupItemRepo groupItemRepo, AvatarItemRepo avatarItemRepo, MailApi api) {
        this.groupItemRepo = groupItemRepo;
        this.avatarItemRepo = avatarItemRepo;
        this.api = api;
        this.context = context;
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

        for (final StudentDto studentDto : studentDtoList){
            Bitmap avatar = avatarItemRepo.findById(studentDto.getId());
            if (avatar == null){
                Picasso.with(context).load(studentDto.getAvatar()).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        avatarItemRepo.add(bitmap, studentDto.getId());
                    }

                    @Override
                    public void onBitmapFailed() {

                    }
                });
            }
            studentList.add(new Student(studentDto.getId(), studentDto.getUsername(),
                    studentDto.getFullname(), avatar, studentDto.isOnline()));
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
