package com.example.technopark.group.repo;

import com.example.technopark.group.model.GroupItem;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GroupItemRepoImpl implements GroupItemRepo {
    private final Map<Long, GroupItem> items = new ConcurrentHashMap<>();
    
    @Override
    public GroupItem findById(long id) {
        return items.get(id);
    }

    @Override
    public GroupItem add(GroupItem groupItem) {
        items.put(groupItem.getId(), groupItem);
        return groupItem;
    }

    @Override
    public void update(GroupItem groupItem) {
        items.put(groupItem.getId(), groupItem);
    }

    @Override
    public void removeById(long id) {
        items.remove(id);
    }
}
