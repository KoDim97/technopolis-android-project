package com.example.technopark.screens.profile;

import android.annotation.SuppressLint;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.technopark.R;
import com.example.technopark.profile.model.UserGroup;
import com.example.technopark.profile.model.UserProfile;
import com.example.technopark.screens.common.mvp.MvpViewObservableBase;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


// TODO: fix bind data
public class ProfileViewMvpImpl extends MvpViewObservableBase<ProfileMvpView.Listener> implements ProfileMvpView {

    private final CircleImageView image;
    private final TextView name;
    private final TextView status;
    private final TextView about;
    private final LinearLayout groupLinearLayout;
    private final TextView mobilePhone;
    private final TextView email;
    private final TextView odnoklassniki;
    private final TextView gitHub;
    private final TextView vkontakte;
    private final ProfileFragment profileFragment;

    private final float scale;

    private static final String idGroupTemplate = "group_button_";
//    private static final Typeface typeface = Typeface.createFromAsset(gey, );

    public ProfileViewMvpImpl(LayoutInflater layoutInflater, ViewGroup parent, ProfileFragment profileFragment) {
        this.profileFragment = profileFragment;
        setRootView(layoutInflater.inflate(R.layout.profile_fragment, parent, false));
        scale = getContext().getResources().getDisplayMetrics().density;
        image = findViewById(R.id.profile_image);
        name = findViewById(R.id.profile_fullname);
        status = findViewById(R.id.profile_status);
        about = findViewById(R.id.profile_about);
        groupLinearLayout = findViewById(R.id.groups);
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
        Picasso.get().load(userProfile.getAvatarUrl()).fit().into(image);
        name.setText(userProfile.getFullName());
        status.setText(userProfile.getMainGroup());
        about.setText(userProfile.getAbout());
//        Добавляем кнопки для просмотра групп
        addGroupsButtons(userProfile.getGroups());
//        mobilePhone.setText(userProfile.getContacts().get(0).getValue());
//        odnoklassniki.setText(userProfile.getAccounts().get(0).getValue());

    }


    private void addGroupsButtons(List<UserGroup> groups) {
        int length = groups.size();

        for (int i = 0; i < length; i++) {
            int style = R.attr.borderlessButtonStyle;
            Button button = new Button(new ContextThemeWrapper(getContext(), style), null, style);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(0, 1, 0,0);
            button.setBackgroundResource(R.color.colorWhite);
            Drawable icon = getContext().getDrawable(R.drawable.ic_chevron_right_black_24dp);
            button.setCompoundDrawables(null, null, icon, null);
            button.setText(groups.get(i).getName());
            button.setGravity(Gravity.CENTER_VERTICAL);
            button.setLayoutParams(params);
//            Конвертируем px в dp
            button.setPadding((int) (13 * scale + 0.5f), 0, 0, 0);
            button.setAllCaps(false);
            button.setId(i);

            button.setOnClickListener(profileFragment);
            groupLinearLayout.addView(button);
//            Не добавляем сепаратор последнему элементу
//            if (i + 1 != length) {
        }
    }

}

