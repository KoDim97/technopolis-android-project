package com.example.technopark.screens.grouplist;

import android.text.Editable;

import com.example.technopark.group.model.GroupItem;
import com.example.technopark.group.model.Student;
import com.example.technopark.screens.common.mvp.MvpViewObservable;
import com.example.technopark.screens.common.nav.BackPressedListener;

import java.util.List;

public interface GroupListMvpView extends MvpViewObservable<GroupListMvpView.Listener> {
    interface Listener extends BackPressedListener {

        void onFilterTextUpdated(String text);
        void onStudentClicked(String username);

        void onBtnGoBackClicked();
    }

    void showProgress();

    void hideProgress();

    void bindData(GroupItem groupItem);
}
