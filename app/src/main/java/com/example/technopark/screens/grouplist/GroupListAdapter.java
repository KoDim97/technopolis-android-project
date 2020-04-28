package com.example.technopark.screens.grouplist;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.technopark.group.model.Student;
import com.example.technopark.screens.grouplist.row.GroupListRowMvpView;
import com.example.technopark.screens.grouplist.row.GroupListRowMvpViewImpl;

import java.util.ArrayList;
import java.util.List;

public class GroupListAdapter extends RecyclerView.Adapter<GroupListAdapter.GroupListViewHolder> implements
        GroupListRowMvpView.Listener {

    static class GroupListViewHolder extends RecyclerView.ViewHolder {

        private GroupListRowMvpView groupListRowMvpView;

        public GroupListViewHolder(GroupListRowMvpView groupListRowMvpView) {
            super(groupListRowMvpView.getRootView());
            this.groupListRowMvpView = groupListRowMvpView;
        }
    }

    private final LayoutInflater layoutInflater;
    private final GroupListRowMvpView.Listener listener;

    private final List<Student> items = new ArrayList<>();

    public GroupListAdapter(LayoutInflater layoutInflater,
                            GroupListRowMvpView.Listener listener) {
        this.layoutInflater = layoutInflater;
        this.listener = listener;
    }

    @NonNull
    @Override public GroupListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        GroupListRowMvpView groupListRowMvpView = new GroupListRowMvpViewImpl(layoutInflater, null, this);
        return new GroupListViewHolder(groupListRowMvpView);
    }

    @Override public void onBindViewHolder(@NonNull GroupListViewHolder groupListViewHolder, int position) {
        Student student = items.get(position);
        groupListViewHolder.groupListRowMvpView.bindData(student);
    }

    @Override public int getItemCount() {
        return items.size();
    }

    @Override public void onStudentClicked(long studentId) {
        listener.onStudentClicked(studentId);
    }

    public void bindData(List<Student> students) {
        items.clear();
        items.addAll(students);
        notifyDataSetChanged();
    }
}
