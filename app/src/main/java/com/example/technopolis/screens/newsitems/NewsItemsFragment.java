package com.example.technopolis.screens.newsitems;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.technopolis.App;
import com.example.technopolis.BaseActivity;
import com.example.technopolis.R;
import com.example.technopolis.api.ApiHelper;
import com.example.technopolis.log.LogHelper;
import com.example.technopolis.news.service.NewsItemService;
import com.example.technopolis.util.ThreadPoster;

public class NewsItemsFragment extends Fragment {

    private NewsItemsPresenter presenter;
    private NewsItemsMvpViewImpl view;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static boolean isSubscriptions = false;
    private static boolean checkSubs;
    public static Handler handler;

    public static Fragment newInstance() {
        return new NewsItemsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new NewsItemsPresenter(getMainActivity().getScreenNavigator(), getMainActivity(),
                getFindNewsItemService(), getMainThreadPoster(), getContext(), getApiHelper());
        ((App) getMainActivity().getApplication()).setNewsItemsPresenter(presenter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        checkSubs = false;
        ((BaseActivity) getActivity()).getRootViewController().setBarVisible(View.VISIBLE);

        view = new NewsItemsMvpViewImpl(inflater, container, getContext(), (App) getMainActivity().getApplication());

        presenter.bindView(view);

        final RadioGroup radioGroup = view.getView().findViewById(R.id.activity_news__top_bar);

        swipeRefreshLayout = view.getView().findViewById(R.id.swiperefresh_items);

        if (isSubscriptions) {
            presenter.subsItems();
            provideListener(presenter::updateDataSubs);
        } else {
            presenter.newsItems();
            provideListener(presenter::updateDataNews);
        }

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (R.id.activity_news__radio_subs == checkedId) {
                LogHelper.i(this, "Changed to subs");
                isSubscriptions = true;
                presenter.getThread().interrupt();
                presenter.subsItems();
                provideListener(presenter::updateDataSubs);
            } else {
                if (checkSubs) {
                    isSubscriptions = false;
                    LogHelper.i(this, "Changed to news");
                    presenter.getThread().interrupt();
                    presenter.newsItems();
                    provideListener(presenter::updateDataNews);
                }
            }
            checkSubs = true;
        });

        return view.getRootView();
    }

    private void provideListener(Runnable method) {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            LogHelper.i(this, "Init refresh");
            method.run();
            handler = new Handler(Looper.getMainLooper()) {
                @Override
                public void handleMessage(@NonNull Message msg) {
                    super.handleMessage(msg);
                    swipeRefreshLayout.setRefreshing(false);
                    LogHelper.i(this, "data refreshed");
                }
            };

        });
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


    @Nullable
    private BaseActivity getMainActivity() {
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

    private ApiHelper getApiHelper() {
        App app = (App) getActivity().getApplication();
        assert app != null;
        return app.provideApiHelper();
    }
}
