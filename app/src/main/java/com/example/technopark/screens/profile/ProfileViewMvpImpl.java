package com.example.technopark.screens.profile;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.technopark.R;
import com.example.technopark.profile.model.UserAccount;
import com.example.technopark.profile.model.UserContact;
import com.example.technopark.profile.model.UserGroup;
import com.example.technopark.profile.model.UserProfile;
import com.example.technopark.screens.common.mvp.MvpViewObservableBase;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


// TODO: fix bind data
public class ProfileViewMvpImpl extends MvpViewObservableBase<ProfileMvpView.Listener>
        implements ProfileMvpView, View.OnLongClickListener, View.OnClickListener {

    private final CircleImageView image;
    private final TextView name;
    private final TextView status;
    private final TextView about;
    private final  androidx.appcompat.widget.Toolbar toolbar;
    //    private final TextView rating;
    private final LinearLayout groupsLinearLayout;
    private final LinearLayout contactsLinearLayout;
    private final LinearLayout accountsLinearLayout;
    private final ProfileFragment profileFragment;

    private final float scale;

    public ProfileViewMvpImpl(LayoutInflater layoutInflater, ViewGroup parent, ProfileFragment profileFragment) {
        this.profileFragment = profileFragment;
        setRootView(layoutInflater.inflate(R.layout.profile_fragment, parent, false));
        scale = getContext().getResources().getDisplayMetrics().density;
        image = findViewById(R.id.profile_image);
        name = findViewById(R.id.profile_fullname);
        status = findViewById(R.id.profile_status);
        about = findViewById(R.id.profile_about);
        toolbar = findViewById(R.id.toolbar);
//        rating = findViewById(R.id.profile_rating);
        groupsLinearLayout = findViewById(R.id.groups);
        contactsLinearLayout = findViewById(R.id.contacts);
        accountsLinearLayout = findViewById(R.id.accounts);
    }

    @Override
    public void showBackButton(String backButtonText) {
        toolbar.setNavigationIcon(R.drawable.ic_navigate_before_black_24dp);
        TextView view = findViewById(R.id.back_to_group);
        view.setText(backButtonText);
        view.setVisibility(View.VISIBLE);
    }

    @Override
    public void bindData(UserProfile userProfile) {
        Picasso.get().load(userProfile.getAvatarUrl()).fit().into(image);
        name.setText(userProfile.getFullName());
        status.setText(userProfile.getMainGroup());
        about.setText(userProfile.getAbout());
//        Добавляем кнопки для просмотра групп
        addGroupsButtons(userProfile.getGroups());
//        Добавляем textView для контактов
        addContactsTextViews(userProfile.getContacts());
//        Добавляем textView для аккаунтов
        addAccountsTextViews(userProfile.getAccounts());

    }
    @Override
    public boolean onLongClick(View v) {
        TextView textView = (TextView) v;
        String text = textView.getText().toString();
        for (Listener listener : getListeners()) {
            listener.onLongClick((Activity) getContext(), text);
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        for (Listener listener : getListeners()) {
            listener.onGroupButtonClicked(v.getId());
        }
    }

    private void addGroupsButtons(List<UserGroup> groups) {
        int length = groups.size();

        for (int i = 0; i < length; i++) {
//            Подгатавливаем и вставляем новую кнопку перехода на список группы
            int style = R.attr.borderlessButtonStyle;
            Button button = new Button(new ContextThemeWrapper(getContext(), style), null, style);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    (int) (43 * scale + 0.5f)
            );

            params.setMargins(0, 1, 0, 0);
            button.setBackgroundResource(R.color.colorWhite);
            Drawable icon = getContext().getDrawable(R.drawable.ic_chevron_right_black_24dp);
            button.setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null);
            button.setText(groups.get(i).getName());
            button.setGravity(Gravity.CENTER_VERTICAL);
            button.setLayoutParams(params);
//            Конвертируем px в dp
            button.setPadding((int) (13 * scale + 0.5f), 0, 0, 0);
            button.setAllCaps(false);
            button.setId((int) groups.get(i).getId());

            button.setOnClickListener(this);
            groupsLinearLayout.addView(button);

////           Добавляем сепаратор, если необходимо
//            LinearLayout.LayoutParams separatorParams = new LinearLayout.LayoutParams(
//                    (int) (100 * scale + 0.5f),
//                    (int) (100 * scale + 0.5f)
//            );
//            View separator = new View(getContext());
//            separator.setBackgroundResource(R.color.colorBlack);
//            separatorParams.gravity = Gravity.LEFT;
//            groupLinearLayout.addView(separator);

        }
    }

    private void addContactsTextViews(List<UserContact> contacts) {
        int length = contacts.size();

        for (int i = 0; i < length; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    (int) (43 * scale + 0.5f)
            );

            params.setMargins(0, 1, 0, 0);
            TextView contact = new TextView(getContext());
            Drawable icon = getContactsIcon(contacts.get(i));
            contact.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
            contact.setText(contacts.get(i).getValue());
            contact.setBackgroundResource(R.color.colorWhite);
            contact.setPadding((int) (12 * scale + 0.5f), 0, 0, 0);
            contact.setCompoundDrawablePadding((int) (10 * scale + 0.5f));
            contact.setAllCaps(false);
            contact.setGravity(Gravity.CENTER_VERTICAL);
            contact.setTextColor(getContext().getResources().getColor(R.color.colorBlack));
            contact.setLayoutParams(params);
            contact.setOnLongClickListener(this);
            contactsLinearLayout.addView(contact);
        }
    }

    private Drawable getContactsIcon(UserContact contact) {
        Context context = getContext();
        switch (contact.getName()) {
            case "email":
                return context.getDrawable(R.drawable.icons8_mail_96);
            default:
                return context.getDrawable(R.drawable.icons8_iphone_96);
        }
    }

    private void addAccountsTextViews(List<UserAccount> accounts) {
        int length = accounts.size();

        for (int i = 0; i < length; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    (int) (43 * scale + 0.5f)
            );
            params.setMargins(0, 1, 0, 0);
            TextView account = new TextView(getContext());
            Drawable icon = getAccountsIcon(accounts.get(i));
            account.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
            account.setText(accounts.get(i).getValue());
            account.setBackgroundResource(R.color.colorWhite);
            account.setPadding((int) (12 * scale + 0.5f), 0, 0, 0);
            account.setCompoundDrawablePadding((int) (10 * scale + 0.5f));
            account.setAllCaps(false);
            account.setGravity(Gravity.CENTER_VERTICAL);
            account.setTextColor(getContext().getResources().getColor(R.color.colorBlack));
            account.setLayoutParams(params);
            account.setOnLongClickListener(this);
            accountsLinearLayout.addView(account);
        }
    }

    private Drawable getAccountsIcon(UserAccount account) {
        Context context = getContext();
        switch (account.getName()) {
            case "github":
                return context.getDrawable(R.drawable.ic_github_mark_24);
            case "vkontakte":
                return context.getDrawable(R.drawable.ic_vk_logo_24);
            default:
                return context.getDrawable(R.drawable.ic_ok_48);
        }
    }


}

