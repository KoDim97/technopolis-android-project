package com.example.technopolis.screens.authorization;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.technopolis.BaseActivity;
import com.example.technopolis.R;
import com.example.technopolis.screens.common.nav.ScreenNavigator;

public class AuthorizationFragment extends Fragment {

    private BaseActivity activity;
    private AuthorizationViewController viewController;
    private ScreenNavigator screenNavigator;

    AuthorizationFragment(BaseActivity activity) {
        this.activity = activity;
        screenNavigator = activity.getScreenNavigator();
    }

    public static Fragment newInstance(BaseActivity baseActivity) {
        Fragment fragment = new AuthorizationFragment(baseActivity);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.authorization, container, false);
        activity.getRootViewController().setBarVisible(View.GONE);
        viewController = new AuthorizationViewController(rootView, activity);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    private BaseActivity getMainActivity() {
        return activity;
    }


}
