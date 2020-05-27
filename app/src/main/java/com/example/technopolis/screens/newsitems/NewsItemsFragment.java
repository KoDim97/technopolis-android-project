package com.example.technopolis.screens.newsitems;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.technopolis.App;
import com.example.technopolis.BaseActivity;
import com.example.technopolis.R;
import com.example.technopolis.api.ApiHelper;
import com.example.technopolis.news.service.NewsItemService;
import com.example.technopolis.screens.common.nav.BackPressDispatcher;
import com.example.technopolis.util.ThreadPoster;

import me.everything.android.ui.overscroll.IOverScrollDecor;
import me.everything.android.ui.overscroll.IOverScrollStateListener;
import me.everything.android.ui.overscroll.IOverScrollUpdateListener;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

import static me.everything.android.ui.overscroll.IOverScrollState.STATE_BOUNCE_BACK;
import static me.everything.android.ui.overscroll.IOverScrollState.STATE_DRAG_START_SIDE;

public class NewsItemsFragment extends Fragment {

    private NewsItemsPresenter presenter;
    private NewsItemsMvpViewImpl view;
    private float overScrollOffset = 0;

    public static Fragment newInstance() {
        return new NewsItemsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new NewsItemsPresenter(getMainActivity().getScreenNavigator(), getMainActivity(),
                getFindNewsItemService(), getMainThreadPoster(), getContext(), getApiHelper());
        presenter.newsItems();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        ((BaseActivity) getActivity()).getRootViewController().setBarVisible(View.VISIBLE);

        view = new NewsItemsMvpViewImpl(inflater, container, getContext(), (App) getMainActivity().getApplication());
        final IOverScrollDecor decor = OverScrollDecoratorHelper.setUpOverScroll(view.getRvNewsItems(),
                OverScrollDecoratorHelper.ORIENTATION_VERTICAL);

        presenter.bindView(view);
        RadioGroup radioGroup = view.getView().findViewById(R.id.activity_news__top_bar);

        provideListener(decor, presenter::updateDataNews);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (R.id.activity_news__radio_subs == checkedId) {
                presenter.subsItems();
                provideListener(decor, presenter::updateDataSubs);
            } else {
                presenter.newsItems();
                provideListener(decor, presenter::updateDataNews);
            }
        });

        return view.getRootView();
    }

    private void provideListener(IOverScrollDecor decor, Runnable method) {
        decor.setOverScrollStateListener((decor12, oldState, newState) -> {
            if (newState == STATE_BOUNCE_BACK && oldState == STATE_DRAG_START_SIDE && overScrollOffset > 100) {
                method.run();
            }
        });
        decor.setOverScrollUpdateListener((decor1, state, offset) -> {
            overScrollOffset = offset;
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
