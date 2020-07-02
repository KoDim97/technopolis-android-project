package com.example.technopolis.screens.grouplist;

import com.example.technopolis.BaseActivity;
import com.example.technopolis.api.ApiHelper;
import com.example.technopolis.group.model.GroupItem;
import com.example.technopolis.group.model.Student;
import com.example.technopolis.group.service.FindGroupItemService;
import com.example.technopolis.screens.common.mvp.MvpPresenter;
import com.example.technopolis.screens.common.nav.BackPressDispatcher;
import com.example.technopolis.screens.common.nav.ScreenNavigator;
import com.example.technopolis.util.ThreadPoster;

import java.util.ArrayList;
import java.util.List;

public class GroupListPresenter implements MvpPresenter<GroupListMvpView>, GroupListMvpView.Listener {

    private final long id;
    private final FindGroupItemService findGroupItemService;
    private final ThreadPoster mainThreadPoster;
    private final ApiHelper apiHelper;
    private ScreenNavigator screenNavigator;
    private BackPressDispatcher backPressDispatcher;
    private static String text;
    private GroupListMvpView view;
    private Thread thread;

    public GroupListPresenter(long id, ScreenNavigator screenNavigator, BaseActivity activity,
                              FindGroupItemService findGroupItemService, ThreadPoster mainThreadPoster, ApiHelper apiHelper) {
        this.id = id;
        this.screenNavigator = screenNavigator;
        this.backPressDispatcher = activity;
        this.findGroupItemService = findGroupItemService;
        this.mainThreadPoster = mainThreadPoster;
        this.apiHelper = apiHelper;
    }

    @Override
    public void bindView(GroupListMvpView view) {
        this.view = view;
        if (!findGroupItemService.isContain(id)) {
            view.showProgress();
        }
        loadItems();
    }

    @Override
    public void onTurnScreen(ScreenNavigator screenNavigator, BaseActivity activity) {
        this.screenNavigator = screenNavigator;
        this.backPressDispatcher = activity;
    }

    private void loadItems() {
        thread = new Thread(() -> {
            GroupItem groupItem = findGroupItemService.findById(id);
            apiHelper.showMessageIfExist(findGroupItemService.getApi(), screenNavigator, this::loadItems);
            if (thread != null && !thread.isInterrupted()) {
                mainThreadPoster.post(() -> {
                    if (groupItem != null) {
                        onItemsLoaded(groupItem);
                    } else {
                        onBackPressed();
                    }
                });
            }
        });
        thread.start();
    }

    private void onItemsLoaded(GroupItem groupItem) {
        // prepare to show
        if (view != null) {
            view.hideProgress();
            view.bindData(groupItem);
        }
    }

    @Override
    public void onStart() {
        if (GroupListPresenter.text != null) {
            onFilterTextUpdated(GroupListPresenter.text);
        }
        view.registerListener(this);
        backPressDispatcher.registerListener(this);
    }

    @Override
    public void onStop() {
        view.unregisterListener(this);
        backPressDispatcher.unregisterListener(this);
    }

    @Override
    public void onDestroy() {
        // dispose all requests
        if (thread != null) {
            thread.interrupt();
        }
        thread = null;
        view = null;
    }

    @Override
    public boolean onBackPressed() {
        GroupListPresenter.text = null;
        screenNavigator.navigateUp();
        return true;
    }

    private boolean checkWord(String[] split_str, String checkText) {
        for (String word : split_str) {
            if (word.length() >= checkText.length()) {
                if (word.substring(0, checkText.length()).equals(checkText)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void onFilterTextUpdated(String text) {
        thread = new Thread(() -> {
            GroupListPresenter.text = text;
            GroupItem groupItem = findGroupItemService.findById(id);
            List<Student> students = groupItem.getStudents();
            List<Student> filteredStudent = new ArrayList<>();

            if (text != null && text.length() != 0) {
                for (Student student : students) {
                    String[] split_str = student.getFullname().toLowerCase().split(" ");
                    String[] lowerCaseText = text.toLowerCase().split(" ");
                    if (lowerCaseText.length == 1) {
                        if (checkWord(split_str, lowerCaseText[0])) {
                            filteredStudent.add(student);
                        }
                    } else {
                        String name = lowerCaseText[0];
                        String surname = lowerCaseText[1];
                        if (checkWord(split_str, name) && checkWord(split_str, surname)) {
                            filteredStudent.add(student);
                        }
                    }
                }
            } else {
                filteredStudent = students;
            }

            GroupItem filteredGroup = new GroupItem(groupItem.getId(), groupItem.getName(), filteredStudent);
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
        GroupListPresenter.text = null;
        screenNavigator.navigateUp();
    }
}
