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

public class AuthorizationFragment extends Fragment {

    private final BaseActivity activity;

    private AuthorizationFragment(@NonNull final BaseActivity activity) {
        this.activity = activity;
    }

    public static Fragment newInstance(@NonNull final BaseActivity baseActivity) {
        return new AuthorizationFragment(baseActivity);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.authorization, container, false);
        activity.getRootViewController().setBarVisible(View.GONE);
        new AuthorizationViewInitializer(rootView, activity);
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



}
