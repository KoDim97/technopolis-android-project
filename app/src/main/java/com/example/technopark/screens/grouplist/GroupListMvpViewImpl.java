package com.example.technopark.screens.grouplist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.technopark.R;
import com.example.technopark.group.model.GroupItem;
import com.example.technopark.group.model.Student;
import com.example.technopark.screens.common.mvp.MvpViewObservableBase;
import com.example.technopark.screens.grouplist.row.GroupListRowMvpView;

import java.util.List;
import java.util.Objects;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class GroupListMvpViewImpl extends MvpViewObservableBase<GroupListMvpView.Listener>
        implements GroupListMvpView, GroupListRowMvpView.Listener {

    private final RecyclerView rvGroupList;
    private final TextView tvGroupName;
    private final GroupListAdapter groupListAdapter;


    public GroupListMvpViewImpl(LayoutInflater layoutInflater, ViewGroup parent, Context context) {
        setRootView(layoutInflater.inflate(R.layout.grouplist_fragment, parent, false));

        tvGroupName = findViewById(R.id.grouplist_fragment__groupname);
        groupListAdapter = new GroupListAdapter(layoutInflater, this);
        rvGroupList = findViewById(R.id.grouplist_fragment__rv);
        rvGroupList.setLayoutManager(new LinearLayoutManager(context));
        rvGroupList.setAdapter(groupListAdapter);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(getContext(), R.drawable.divider)));
        rvGroupList.addItemDecoration(itemDecorator);
        OverScrollDecoratorHelper.setUpOverScroll(rvGroupList, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);


    }

    @Override
    public void bindData(GroupItem groupItem) {
        tvGroupName.setText(groupItem.getName());
        groupListAdapter.bindData(groupItem.getStudents());
    }

    @Override
    public void onStudentClicked(long studentId) {
        for (Listener listener : getListeners()){
            listener.onStudentClicked(studentId);
        }
    }
}
