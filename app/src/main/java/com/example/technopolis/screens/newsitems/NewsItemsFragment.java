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

    public static Fragment newInstance() {
        return new NewsItemsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new NewsItemsPresenter(getMainActivity().getScreenNavigator(), getMainActivity(),
                getFindNewsItemService(), getMainThreadPoster(), getContext(), getApiHelper());
        presenter.newsItems();
//        presenter.subsItems();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        ((BaseActivity) getActivity()).getRootViewController().setBarVisible(View.VISIBLE);

        final NewsItemsMvpViewImpl view = new NewsItemsMvpViewImpl(inflater, container, getContext(),(App)getMainActivity().getApplication());
        final IOverScrollDecor decor = OverScrollDecoratorHelper.setUpOverScroll(view.getRvNewsItems(),
                OverScrollDecoratorHelper.ORIENTATION_VERTICAL);

        presenter.bindView(view);

        RadioGroup radioGroup = view.getView().findViewById(R.id.activity_news__top_bar);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (R.id.activity_news__radio_subs == checkedId) {
                presenter.subsItems();
                decor.setOverScrollStateListener((decor1, oldState, newState) -> {
                    if (newState == STATE_BOUNCE_BACK && oldState == STATE_DRAG_START_SIDE) {
                        presenter.updateDataSubs();
                    }
                });
            } else {
                presenter.newsItems();
                decor.setOverScrollStateListener((decor12, oldState, newState) -> {
                    if (newState == STATE_BOUNCE_BACK) {
                        if (oldState == STATE_DRAG_START_SIDE) {
                            presenter.updateDataNews();
                        }
                    }
                });
            }
        });


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
