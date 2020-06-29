package com.example.technopolis.screens.scheduleritems;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.technopolis.App;
import com.example.technopolis.BaseActivity;
import com.example.technopolis.api.ApiHelper;
import com.example.technopolis.scheduler.service.SchedulerItemService;
import com.example.technopolis.util.ThreadPoster;

public class SchedulerFragment extends Fragment {


    private SchedulerItemsPresenter presenter;

    public static Fragment newInstance() {
        return new SchedulerFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new SchedulerItemsPresenter(getBaseActivity().getScreenNavigator(), getBaseActivity(),
                getFindBlogItemService(), getMainThreadPoster(), getApiHelper());
        ((App) getBaseActivity().getApplication()).setSchedulerItemsPresenter(presenter);
    }

    @Override
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        SchedulerItemsMvpView view = new SchedulerItemsMvpViewImpl(inflater, container, getContext());
        presenter.bindView(view);

        getBaseActivity().getRootViewController().setBarVisible(View.VISIBLE);
        return view.getRootView();
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
    private BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    private SchedulerItemService getFindBlogItemService() {
        App app = (App) getActivity().getApplication();
        assert app != null;
        return app.provideSchedulerItemService();
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
