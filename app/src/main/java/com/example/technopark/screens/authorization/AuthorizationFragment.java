package com.example.technopark.screens.authorization;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.technopark.BaseActivity;
import com.example.technopark.R;
import com.example.technopark.adapter.MyPager;
import com.google.android.material.tabs.TabLayout;

import static android.view.KeyEvent.KEYCODE_ENTER;

public class AuthorizationFragment extends Fragment {

    private BaseActivity activity;
    private AuthorizationPresenter presenter;

    AuthorizationFragment(BaseActivity activity){
        this.activity=activity;
        presenter = new AuthorizationPresenter(getMainActivity());
        AuthorizationMvpView view = new AuthorizationMvpViewImpl(activity);
        presenter.bindView(view);
    }

    public static Fragment newInstance(BaseActivity baseActivity) {
        Fragment fragment = new AuthorizationFragment(baseActivity);
        return fragment;
    }

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new AuthorizationPresenter(getMainActivity()); //getMainActivity().getScreenNavigator(), getMainActivity());
    }

    @Nullable @Override public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                                                 @Nullable Bundle savedInstanceState) {
        AuthorizationMvpView view = new AuthorizationMvpViewImpl(inflater, container, activity);
        presenter.bindView(view);
        return view.getRootView();
    }

    @Override public void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override public void onStop() {
        presenter.onStop();
        super.onStop();
    }

    @Override public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Nullable private BaseActivity getMainActivity() {
        return activity;
    }

}
