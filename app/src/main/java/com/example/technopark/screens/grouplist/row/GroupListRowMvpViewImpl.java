package com.example.technopark.screens.grouplist.row;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.technopark.R;
import com.example.technopark.group.model.Student;
import com.example.technopark.screens.common.mvp.MvpViewBase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class GroupListRowMvpViewImpl extends MvpViewBase
        implements GroupListRowMvpView {

    private final Listener listener;

    private final TextView tvName;
    private final CircleImageView ivAvatar;

    private Student student;

    public GroupListRowMvpViewImpl(LayoutInflater layoutInflater, ViewGroup parent,
                                   GroupListRowMvpView.Listener listener) {
        this.listener = listener;
        setRootView(layoutInflater.inflate(R.layout.student_item_row, parent, false));

        tvName = findViewById(R.id.person_item__name);
        ivAvatar = findViewById(R.id.person_item__image);

        getRootView().setOnClickListener(view -> onStudentClicked());
    }

    @Override
    public void bindData(Student student) {
        this.student = student;
        tvName.setText(student.getFullname());
        Picasso.get().load(student.getAvatarUrl()).fit().into(ivAvatar);
    }

    public void onStudentClicked() {
        listener.onStudentClicked(student.getId());
    }
    
}
