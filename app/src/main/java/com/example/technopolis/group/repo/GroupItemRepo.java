package com.example.technopolis.group.repo;

import com.example.technopolis.group.model.GroupItem;

public interface GroupItemRepo {

    GroupItem findById(long id);

    GroupItem add(GroupItem groupItem);

    void update(GroupItem groupItem);

    void removeById(long id);
}
