package com.example.technopolis.screens.profile;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.technopolis.R;
import com.example.technopolis.profile.model.UserAccount;
import com.example.technopolis.profile.model.UserContact;
import com.example.technopolis.profile.model.UserGroup;
import com.example.technopolis.profile.model.UserProfile;
import com.example.technopolis.screens.common.mvp.MvpViewObservableBase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileViewMvpImpl extends MvpViewObservableBase<ProfileMvpView.Listener>
        implements ProfileMvpView, View.OnClickListener {

    private final CircleImageView image;
    private final TextView name;
    private final TextView status;
    private final TextView about;
    private final androidx.appcompat.widget.Toolbar toolbar;
    private final TextView toolbarTextView;
    private final LinearLayout groupsLinearLayout;
    private final RelativeLayout marksRelativeLayout;
    private final LinearLayout contactsLinearLayout;
    private final LinearLayout accountsLinearLayout;
    private final LinearLayout profileWrapper;
    private final FrameLayout profileContentContainer;
    private final Map<Integer, String> textViewsUrls;

    private final float scale;

    public ProfileViewMvpImpl(LayoutInflater layoutInflater, ViewGroup parent) {
        setRootView(layoutInflater.inflate(R.layout.profile_fragment, parent, false));
        scale = getContext().getResources().getDisplayMetrics().density;
        profileContentContainer = findViewById(R.id.profile_content_container);
        image = findViewById(R.id.profile_image);
        name = findViewById(R.id.profile_fullname);
        status = findViewById(R.id.profile_status);
        about = findViewById(R.id.profile_about);
        toolbar = findViewById(R.id.toolbar);
        toolbarTextView = findViewById(R.id.toolbar__text_view);
        groupsLinearLayout = findViewById(R.id.groups);
        marksRelativeLayout = findViewById(R.id.marks);
        contactsLinearLayout = findViewById(R.id.contacts);
        accountsLinearLayout = findViewById(R.id.accounts);
        profileWrapper = findViewById(R.id.profile_wrapper);

        textViewsUrls = new HashMap<>();
    }

    @Override
    public void showBackButton(String backButtonText) {
        toolbar.setNavigationIcon(R.drawable.ic_navigate_before_black_24dp);
        TextView view = findViewById(R.id.back_to_group);
        view.setText(backButtonText);
        view.setVisibility(View.VISIBLE);
        toolbar.setNavigationOnClickListener(v -> goBack());
        view.setOnClickListener(v -> goBack());
    }

    private void goBack() {
        for (Listener listener : getListeners()) {
            listener.onBtnGoBackClicked();
        }
    }

    @Override
    public void bindData(UserProfile userProfile) {
        if (userProfile.getAvatar() != null) {
            image.setImageBitmap(userProfile.getAvatar());
        } else {
            image.setImageResource(R.drawable.img_no_avatar);
        }
        profileContentContainer.setVisibility(View.VISIBLE);
        name.setText(userProfile.getFullName());
        status.setText(userProfile.getMainGroup());

        String aboutText = userProfile.getAbout();
        if (aboutText.equals("null")) {
            aboutText = getContext().getString(R.string.aboutPlaceholder);
        }
        about.setText(aboutText);

        marksRelativeLayout.setOnClickListener(v -> onMarksClick(userProfile.getUserName()));

//        Добавляем кнопки для просмотра групп
        addGroupsButtons(userProfile.getGroups());
//        Добавляем textView для контактов
        addContactsTextViews(userProfile.getContacts());
//        Добавляем textView для аккаунтов
        addAccountsTextViews(userProfile.getAccounts());
    }

    private void onMarksClick(String username) {
        for (Listener listener : getListeners()) {
            listener.onMarksClick(username);
        }
    }

    private void onContactClick(View v) {
        TextView textView = (TextView) v;
        String contactInfo = textView.getText().toString();
        for (Listener listener : getListeners()) {
            listener.onContactClick((Activity) getContext(), contactInfo);
        }
    }

    private void onAccountClick(View v) {
        TextView textView = (TextView) v;
        String text = textView.getText().toString();
        String name = textViewsUrls.get(textView.getId());
        for (Listener listener : getListeners()) {
            listener.onAccountClick((Activity) getContext(), text, name);
        }
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

            @SuppressLint("ResourceType")
            Button button = new Button(new ContextThemeWrapper(getContext(), style), null, style);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    (int) (43 * scale + 0.5f)
            );

            params.setMargins(0, (int) (1 * scale + 0.5f), 0, 0);
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
        }

        if (length == 0) {
            groupsLinearLayout.setVisibility(View.GONE);
        }
    }

    private void addContactsTextViews(List<UserContact> contacts) {
        int length = contacts.size();

        for (int i = 0; i < length; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    (int) (43 * scale + 0.5f)
            );

            params.setMargins(0, (int) (1 * scale + 0.5f), 0, 0);
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
            contact.setOnClickListener(this::onContactClick);
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

            System.out.println(accounts.get(i).getName());
            System.out.println(accounts.get(i).getValue());

            params.setMargins(0, (int) (1 * scale + 0.5f), 0, 0);
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
            account.setOnClickListener(this::onAccountClick);
            account.setId(i);
            textViewsUrls.put(account.getId(), accounts.get(i).getName());
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
            case "odnoklassniki":
                return context.getDrawable(R.drawable.ic_ok_48);
            case "facebook":
                return context.getDrawable(R.drawable.ic_fb_24);
            case "skype":
                return context.getDrawable(R.drawable.ic_skype_28);
            case "tamtam":
                return context.getDrawable(R.drawable.ic_tamtam_24);
            case "telegram":
                return context.getDrawable(R.drawable.ic_telegram_24);
            case "bitbucket":
                return context.getDrawable(R.drawable.ic_bitbucket_24);
            default:
                return context.getDrawable(R.drawable.ic_mail_agent_24);
        }

    }


    public void showExitButton() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                (int) (43 * scale + 0.5f)
        );
        params.setMargins(0, (int) (15 * scale + 0.5f), 0, (int) (70 * scale + 0.5f));
        int style = R.attr.borderlessButtonStyle;

        @SuppressLint("ResourceType")
        Button exitButton = new Button(new ContextThemeWrapper(getContext(), style), null, style);

        exitButton.setText(R.string.exit);
        exitButton.setBackgroundResource(R.color.colorWhite);
        exitButton.setTextColor(getContext().getResources().getColor(R.color.colorRed));
        exitButton.setAllCaps(false);
        exitButton.setLayoutParams(params);

        profileWrapper.addView(exitButton);

        exitButton.setOnClickListener(v -> {
            final Dialog exitDialog = new Dialog(getContext(), R.style.ExitDialogAnimation);
            exitDialog.getWindow().getAttributes().windowAnimations = R.style.ExitDialogAnimation;
            exitDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(50, 0, 0, 0)));
            exitDialog.setContentView(R.layout.exit_popup_view);
            exitDialog.setCancelable(true);
            exitDialog.show();

            exitDialog.findViewById(R.id.exitButtonApprove).setOnClickListener(v1 -> {
                exitDialog.dismiss();
                for (Listener listener : getListeners()) {
                    listener.onSignOutClicked();
                }

            });
            exitDialog.findViewById(R.id.cancelButton).setOnClickListener(v1 -> exitDialog.dismiss());
        });
    }

    @Override
    public void showNameOnToolbar(String userName) {
        toolbarTextView.setText(userName);
    }

}

