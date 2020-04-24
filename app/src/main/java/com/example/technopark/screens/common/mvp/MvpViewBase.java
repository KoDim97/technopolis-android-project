package com.example.technopark.screens.common.mvp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.annotation.DrawableRes;
import androidx.annotation.PluralsRes;
import androidx.annotation.StringRes;

public abstract class MvpViewBase implements MvpView {

    private View rootView;

    @Override public View getRootView() {
        return rootView;
    }

    protected void setRootView(View rootView) {
        this.rootView = rootView;
    }

    protected <T extends View> T findViewById(int id) {
        return getRootView().findViewById(id);
    }

    protected Context getContext() {
        return getRootView().getContext();
    }

    protected String getString(@StringRes int id) {
        return getContext().getString(id);
    }

    protected String getString(@StringRes int id, Object... formatArgs) {
        return getContext().getString(id, formatArgs);
    }

    protected String getQuantityString(@PluralsRes int id, int quantity, Object... formatArgs) {
        return getContext().getResources().getQuantityString(id, quantity, formatArgs);
    }

    protected Drawable getDrawable(@DrawableRes int id) {
        return getContext().getDrawable(id);
    }
}
