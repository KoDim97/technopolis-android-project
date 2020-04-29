package com.example.technopark.screens.grouplist;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.technopark.R;
import com.example.technopark.group.model.GroupItem;
import com.example.technopark.screens.common.mvp.MvpViewObservableBase;
import com.example.technopark.screens.grouplist.row.GroupListRowMvpView;

import java.util.Objects;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class GroupListMvpViewImpl extends MvpViewObservableBase<GroupListMvpView.Listener>
        implements GroupListMvpView, GroupListRowMvpView.Listener {

    private final ProgressBar progress;
    private final LinearLayout llContent;
    private final RecyclerView rvGroupList;
    private final TextView tvGroupName;
    private final GroupListAdapter groupListAdapter;
    private final EditText searchField;
    private final Button clearButton;
    private final TextView backClickText;
    private final Toolbar toolbar;


    public GroupListMvpViewImpl(LayoutInflater layoutInflater, ViewGroup parent, Context context) {
        setRootView(layoutInflater.inflate(R.layout.grouplist_fragment, parent, false));

        progress = findViewById(R.id.grouplist_fragment__progress);
        llContent = findViewById(R.id.grouplist_fragment__llContainer);
        tvGroupName = findViewById(R.id.grouplist_fragment__groupname);
        searchField = findViewById(R.id.grouplist_fragment__searchfield);
        clearButton = findViewById(R.id.grouplist_fragment__clearbutton);
        backClickText = findViewById(R.id.grouplist_fragment__canceltext);
        toolbar = findViewById(R.id.grouplist_fragment__topbar);

        groupListAdapter = new GroupListAdapter(layoutInflater, this);
        rvGroupList = findViewById(R.id.grouplist_fragment__rv);
        rvGroupList.setLayoutManager(new LinearLayoutManager(context));
        rvGroupList.setAdapter(groupListAdapter);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(getContext(), R.drawable.divider)));
        rvGroupList.addItemDecoration(itemDecorator);
        OverScrollDecoratorHelper.setUpOverScroll(rvGroupList, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);

        backClickText.setOnClickListener(v -> goBack());
        toolbar.setNavigationOnClickListener(v -> goBack());
        clearButton.setOnClickListener(v -> clearText());

        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString();
                if (text.length() != 0){
                    clearButton.setVisibility(View.VISIBLE);
                }else {
                    clearButton.setVisibility(View.INVISIBLE);
                }
                filterList(text);
            }
        });

    }

    private void clearText() {
        for (Listener listener : getListeners()) {
            searchField.setText("");
            listener.onFilterTextUpdated("");
        }
    }


    private void filterList(String s){
        for (Listener listener : getListeners()) {
            listener.onFilterTextUpdated(s);
        }
    }

    private void goBack() {
        for (Listener listener : getListeners()) {
            listener.onBtnGoBackClicked();
        }
    }

    @Override
    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
        llContent.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        progress.setVisibility(View.GONE);
        llContent.setVisibility(View.VISIBLE);
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
