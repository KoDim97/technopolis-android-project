package com.example.technopark.screens.grouplist;

import android.text.Editable;

import com.example.technopark.group.model.GroupItem;
import com.example.technopark.group.model.Student;
import com.example.technopark.group.service.FindGroupItemService;
import com.example.technopark.screens.common.mvp.MvpPresenter;
import com.example.technopark.screens.common.nav.BackPressDispatcher;
import com.example.technopark.screens.common.nav.ScreenNavigator;
import com.example.technopark.screens.profile.ProfileFragment;
import com.example.technopark.util.ThreadPoster;

import java.util.ArrayList;
import java.util.List;

public class GroupListPresenter implements MvpPresenter<GroupListMvpView>,
        GroupListMvpView.Listener {

    private final long id;
    private final ScreenNavigator screenNavigator;
    private final BackPressDispatcher backPressDispatcher;
    private final FindGroupItemService findGroupItemService;
    private final ThreadPoster mainThreadPoster;

    private GroupListMvpView view;
    private Thread thread;

    public GroupListPresenter(long id, ScreenNavigator screenNavigator, BackPressDispatcher backPressDispatcher,
                              FindGroupItemService findGroupItemService, ThreadPoster mainThreadPoster) {
        this.id = id;
        this.screenNavigator = screenNavigator;
        this.backPressDispatcher = backPressDispatcher;
        this.findGroupItemService = findGroupItemService;
        this.mainThreadPoster = mainThreadPoster;
    }

    @Override public void bindView(GroupListMvpView view) {
        this.view = view;
        view.showProgress();
        loadItems();
    }

    private void loadItems() {
        thread = new Thread(() -> {
            GroupItem groupItem = findGroupItemService.findById(id);
            if (!thread.isInterrupted()) {
                mainThreadPoster.post(() -> onItemsLoaded(groupItem));
            }
        });
        thread.start();
    }

    private void onItemsLoaded(GroupItem groupItem) {
        // prepare to show
        view.hideProgress();
        view.bindData(groupItem);
    }

    @Override public void onStart() {
        view.registerListener(this);
        backPressDispatcher.registerListener(this);
    }

    @Override public void onStop() {
        view.unregisterListener(this);
        backPressDispatcher.unregisterListener(this);
    }

    @Override public void onDestroy() {
        // dispose all requests
        thread.interrupt();
        thread = null;
        view = null;
    }

    @Override public boolean onBackPressed() {
        screenNavigator.navigateUp();
        return true;
    }

    @Override
    public void onFilterTextUpdated(String text) {
        thread = new Thread(() -> {
            GroupItem groupItem = findGroupItemService.findById(id);
            List<Student> students = groupItem.getStudents();
            List<Student> filteredStudent = new ArrayList<>();

            if (text.length() != 0) {
                for(Student student: students){
                    if(student.getFullname().toLowerCase().contains(text.toLowerCase())){
                        filteredStudent.add(student);
                    }
                }
            }else{
                filteredStudent = students;
            }

            GroupItem filteredGroup = new GroupItem(groupItem.getId(), groupItem.getName(),filteredStudent);
            if (!thread.isInterrupted()) {
                mainThreadPoster.post(() -> onItemsLoaded(filteredGroup));
            }
        });
        thread.start();
    }

    @Override
    public void onStudentClicked(String username) {
        GroupItem groupItem = findGroupItemService.findById(id);
        screenNavigator.toProfile(username, groupItem.getName());
    }

    @Override
    public void onBtnGoBackClicked() {
        screenNavigator.navigateUp();
    }
}
