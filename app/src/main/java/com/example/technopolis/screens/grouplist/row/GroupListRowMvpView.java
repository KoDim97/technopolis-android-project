package com.example.technopolis.screens.grouplist.row;

import com.example.technopolis.group.model.Student;
import com.example.technopolis.screens.common.mvp.MvpView;

public interface GroupListRowMvpView extends MvpView {
    interface Listener {

        void onStudentClicked(String username);

    }

    void bindData(Student student);
}
