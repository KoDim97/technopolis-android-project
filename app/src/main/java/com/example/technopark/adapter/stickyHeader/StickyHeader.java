package com.example.technopark.adapter.stickyHeader;

import android.view.View;

import androidx.annotation.LayoutRes;

public interface StickyHeader {

    int getHeaderPositionForItem(int itemPosition);

    @LayoutRes
    int getHeaderLayout(int headerPosition);

    void bindHeaderData(View header, int headerPosition);

    boolean isHeader(int position);

}
