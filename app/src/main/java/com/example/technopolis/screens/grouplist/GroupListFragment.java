package com.example.technopolis.screens.grouplist;

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
import com.example.technopolis.group.service.FindGroupItemService;
import com.example.technopolis.util.ThreadPoster;

public class GroupListFragment extends Fragment {

    public static final String ARG_ITEM_ID = "itemId";

    private GroupListPresenter presenter;

    public static Fragment newInstance(long itemId) {
        Fragment fragment = new GroupListFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_ITEM_ID, itemId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        long id = getArguments().getLong(ARG_ITEM_ID);
        //noinspection ConstantConditions
        presenter = new GroupListPresenter(id, getMainActivity().getScreenNavigator(), getMainActivity(),
                getFindGroupItemService(), getMainThreadPoster(), getApiHelper());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        GroupListMvpView view = new GroupListMvpViewImpl(inflater, container, getContext());
        getMainActivity().getRootViewController().setBarVisible(View.GONE);
        presenter.bindView(view);
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
    private BaseActivity getMainActivity() {
        return (BaseActivity) getActivity();
    }

    private FindGroupItemService getFindGroupItemService() {
        App app = (App) getActivity().getApplication();
        assert app != null;
        return app.provideFindGroupItemService();
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
