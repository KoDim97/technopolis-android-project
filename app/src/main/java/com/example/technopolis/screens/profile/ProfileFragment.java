package com.example.technopolis.screens.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.technopolis.App;
import com.example.technopolis.BaseActivity;
import com.example.technopolis.R;
import com.example.technopolis.api.ApiHelper;
import com.example.technopolis.profile.service.ProfileService;
import com.example.technopolis.util.ThreadPoster;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class ProfileFragment extends Fragment {

    private ProfilePresenter presenter;
    private static final String PROFILE_NAME = "user_name";
    private static final String BACK_BUTTON_TEXT = "back_button_text";

    private String userProfile;

    public ProfileFragment() {
    }

    public static Fragment newInstance() {
        Fragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(PROFILE_NAME, "");
        args.putString(BACK_BUTTON_TEXT, "");
        fragment.setArguments(args);
        return fragment;
    }

    public static Fragment newInstance(String username, String backButtonText) {
        Fragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(PROFILE_NAME, username);
        args.putString(BACK_BUTTON_TEXT, backButtonText);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //noinspection ConstantConditions
        Bundle arguments = getArguments();
        userProfile = arguments.getString(PROFILE_NAME);
        String backButtonText = arguments.getString(BACK_BUTTON_TEXT);

        presenter = new ProfilePresenter(userProfile, backButtonText, getProfileService(),
                getBaseActivity().getScreenNavigator(), getMainThreadPoster(), getBaseActivity(),
                getApiHelper());
        ((App) getBaseActivity().getApplication()).setProfilePresenter(presenter);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ProfileViewMvpImpl view = new ProfileViewMvpImpl(inflater, container);

        if (userProfile.equals("")) {
            ((BaseActivity) getContext()).getRootViewController().setBarVisible(View.VISIBLE);
        } else {
            ((BaseActivity) getContext()).getRootViewController().setBarVisible(View.GONE);
        }


        presenter.bindView(view);
        return view.getRootView();
    }

    @Nullable
    private BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    public void onStop() {
        presenter.onStop();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    private ProfileService getProfileService() {
        App app = (App) getActivity().getApplication();
        assert app != null;
        return app.provideProfileService();
    }

    private ThreadPoster getMainThreadPoster() {
        App app = (App) getActivity().getApplication();
        assert app != null;
        return app.provideMainThreadPoster();
    }

    private ApiHelper getApiHelper() {
        App app = (App) getActivity().getApplication();
        assert app != null;
        return app.provideApiHelper();
    }
}
