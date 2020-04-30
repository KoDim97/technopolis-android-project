package com.example.technopark.screens.newsitems;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.technopark.App;
import com.example.technopark.BaseActivity;
import com.example.technopark.R;
import com.example.technopark.news.service.NewsItemService;
import com.example.technopark.util.ThreadPoster;

import me.everything.android.ui.overscroll.IOverScrollDecor;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class NewsItemsFragment extends Fragment{

    private NewsItemsPresenter presenter;

    public static Fragment newInstance() {
        return new NewsItemsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new NewsItemsPresenter(getMainActivity().getScreenNavigator(), getMainActivity(),
                getFindNewsItemService(), getMainThreadPoster(), getContext());

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

//        ((BaseActivity)getActivity()).getRootViewController().setBarVisible(View.VISIBLE);

        final NewsItemsMvpView view = new NewsItemsMvpViewImpl(inflater, container, getContext());
        final IOverScrollDecor decor = OverScrollDecoratorHelper.setUpOverScroll(((NewsItemsMvpViewImpl) view).getRvNewsItems(),
                OverScrollDecoratorHelper.ORIENTATION_VERTICAL);

        presenter.bindView(view);

        RadioGroup radioGroup = ((NewsItemsMvpViewImpl) view).getView().findViewById(R.id.activity_news__top_bar);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (R.id.activity_news__radio_subs == checkedId) {
                presenter.updateDataSubs();
//                    decor.setOverScrollStateListener((decor1, oldState, newState) -> {
//                        switch (newState) {
//                            case STATE_BOUNCE_BACK:
//                                if (oldState == STATE_DRAG_START_SIDE) {
//                                    presenter.updateDataSubs();
//                                }
//                                break;
//                        }
//                    });
            } else {
                presenter.updateDataNews();
//                    decor.setOverScrollStateListener((decor12, oldState, newState) -> {
//                        switch (newState) {
//                            case STATE_BOUNCE_BACK:
//                                if (oldState == STATE_DRAG_START_SIDE) {
//                                    presenter.updateDataNews();
//                                }
//                                break;
//                        }
//                    });
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
}
