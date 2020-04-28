package com.example.technopark.screens.newsitems;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.technopark.App;
import com.example.technopark.BaseActivity;
import com.example.technopark.news.service.NewsItemService;
import com.example.technopark.util.ThreadPoster;

public class NewsItemsFragment extends Fragment{

    private NewsItemsPresenter presenter;

    public static Fragment newInstance() {
        return new NewsItemsFragment();
    }

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable @Override public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                                                 @Nullable Bundle savedInstanceState) {
        NewsItemsMvpView view = new NewsItemsMvpViewImpl(inflater, container, getContext());
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
        return (BaseActivity) getActivity();
    }

    private NewsItemService getFindNewsItemService() {
        App app = (App) getActivity().getApplication();
        assert app != null;
        return app.provideNewsItemService();
    }

    private ThreadPoster getMainThreadPoster() {
        App app = (App) getActivity().getApplication();
        assert app != null;
        return app.provideMainThreadPoster();
    }
}
