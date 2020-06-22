package com.example.technopolis.screens.common;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.technopolis.BaseActivity;
import com.example.technopolis.R;
import com.example.technopolis.screens.common.nav.BackPressedListener;
import com.example.technopolis.screens.common.nav.ScreenNavigator;

public class FeedbackFragment extends Fragment implements BackPressedListener {

    private static final String ARG_FEEDBACK_URL = "FEEDBACK_URL";

    private WebView webView;
    private String url;
    private ScreenNavigator screenNavigator;

    public static Fragment newInstance(String url) {
        Fragment fragment = new FeedbackFragment();
        Bundle args = new Bundle();
        args.putString(ARG_FEEDBACK_URL, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        screenNavigator = getBaseActivity().getScreenNavigator();
        url = getArguments().getString(ARG_FEEDBACK_URL);
    }

    @Override
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.scheduler_feedback_layout, container, false);

        getBaseActivity().registerListener(this);

        webView = (WebView) view.findViewById(R.id.lesson_feedback_view);
        webView.loadUrl(url);
        System.out.println(url);
        webView.setWebViewClient(new WebViewClient());

        getBaseActivity().getRootViewController().setBarVisible(View.GONE);
        return view.getRootView();
    }

    @Nullable
    private BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    @Override
    public boolean onBackPressed() {
        return screenNavigator.navigateUp();
    }

    @Override
    public void onDestroyView() {
        getBaseActivity().unregisterListener(this);
        super.onDestroyView();

    }
}
