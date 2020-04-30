package com.example.technopolis.screens.grouplist;

import com.example.technopolis.group.model.GroupItem;
import com.example.technopolis.screens.common.mvp.MvpViewObservable;
import com.example.technopolis.screens.common.nav.BackPressedListener;

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
