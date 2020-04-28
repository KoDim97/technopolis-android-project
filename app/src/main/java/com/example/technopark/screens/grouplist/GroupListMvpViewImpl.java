package com.example.technopark.screens.grouplist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.technopark.R;
import com.example.technopark.group.model.GroupItem;
import com.example.technopark.group.model.Student;
import com.example.technopark.screens.common.mvp.MvpViewObservableBase;
import com.example.technopark.screens.grouplist.row.GroupListRowMvpView;

import java.util.List;

class GroupListMvpViewImpl extends MvpViewObservableBase<GroupListMvpView.Listener>
        implements GroupListMvpView, GroupListRowMvpView.Listener {

    private final RecyclerView rvGroupList;
    private final GroupListAdapter groupListAdapter;

    public GroupListMvpViewImpl(LayoutInflater layoutInflater, ViewGroup parent, Context context) {
        setRootView(layoutInflater.inflate(R.layout.grouplist_fragment, parent, false));

        groupListAdapter = new GroupListAdapter(layoutInflater, this);
        rvGroupList = findViewById(R.id.grouplist_fragment__rv);
        rvGroupList.setLayoutManager(new LinearLayoutManager(context));
        rvGroupList.setAdapter(groupListAdapter);

    }

    @Override
    public void bindData(GroupItem groupItem) {
        groupListAdapter.bindData(groupItem.getStudents());
    }

    @Override
    public void onStudentClicked(long studentId) {
        for (Listener listener : getListeners()){
            listener.onStudentClicked(studentId);
        }
    }
}
