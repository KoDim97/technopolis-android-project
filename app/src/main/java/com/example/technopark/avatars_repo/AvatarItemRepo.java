package com.example.technopark.avatars_repo;

import android.graphics.Bitmap;

public interface AvatarItemRepo {

    Bitmap findById(long id);

    void add(Bitmap avatar, long id);

    void update(Bitmap avatar, long id);

    void removeById(long id);
}
