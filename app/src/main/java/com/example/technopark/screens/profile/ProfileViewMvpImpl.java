package com.example.technopark.screens.profile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.technopark.R;
import com.example.technopark.profile.model.UserProfile;
import com.example.technopark.screens.common.mvp.MvpViewObservableBase;


// TODO: fix bind data
public class ProfileViewMvpImpl extends MvpViewObservableBase<ProfileMvpView.Listener> implements ProfileMvpView {

    private final TextView name;
    private final TextView status;
    private final TextView about;
    private final Button groupButton;
    private final TextView mobilePhone;
    private final TextView email;
    private final TextView odnoklassniki;
    private final TextView gitHub;
    private final TextView vkontakte;


    public ProfileViewMvpImpl(LayoutInflater layoutInflater, ViewGroup parent) {
        setRootView(layoutInflater.inflate(R.layout.profile_fragment, parent, false));
        name = findViewById(R.id.profile_fullname);
        status = findViewById(R.id.profile_status);
        about = findViewById(R.id.profile_about);
        groupButton = findViewById(R.id.group_1);
        mobilePhone = findViewById(R.id.phone_number);
        email = findViewById(R.id.email);
        odnoklassniki = findViewById(R.id.odnoklassniki);
        gitHub = findViewById(R.id.github);
        vkontakte = findViewById(R.id.vkontakte);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void bindData(UserProfile userProfile) {
        name.setText(userProfile.getFullName());
        status.setText(userProfile.getMainGroup());
        about.setText(userProfile.getAbout());
        groupButton.setText(userProfile.getGroups().get(0).getName());
        mobilePhone.setText(userProfile.getContacts().get(0).getValue());
        odnoklassniki.setText(userProfile.getAccounts().get(0).getValue());
    }
}
