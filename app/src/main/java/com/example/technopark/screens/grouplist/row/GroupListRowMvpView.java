package com.example.technopark.screens.grouplist.row;

import com.example.technopark.group.model.Student;
import com.example.technopark.screens.common.mvp.MvpView;

public interface GroupListRowMvpView extends MvpView {
    interface Listener {

        void onStudentClicked(long studentId);

    }

    void bindData(Student student);
}
