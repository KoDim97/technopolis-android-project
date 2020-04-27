package com.example.technopark.screens.root;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.technopark.BaseActivity;
import com.example.technopark.R;
import com.example.technopark.screens.common.nav.ScreenNavigator;

public class MenuRootFragment extends Fragment {

    private ScreenNavigator screenNavigator;
    MenuRootViewController menuRootViewController;


    public void setBarVisible(int state) {
        menuRootViewController.setBarVisible(state);
    }

    public int getVisibility() {
        return menuRootViewController.getVisibility();
    }

    public static MenuRootFragment newInstance() {
        return new MenuRootFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.navigation_menu, container, false);
        menuRootViewController = new MenuRootViewController(rootView, screenNavigator);
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        screenNavigator = getMainActivity().getScreenNavigator();
    }

    @Nullable
    private BaseActivity getMainActivity() {
        return (BaseActivity) getActivity();
    }
}
