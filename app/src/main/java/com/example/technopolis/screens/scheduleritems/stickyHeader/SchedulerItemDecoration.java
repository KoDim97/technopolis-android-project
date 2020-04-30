package com.example.technopolis.screens.scheduleritems.stickyHeader;

import android.graphics.Canvas;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

public class SchedulerItemDecoration extends RecyclerView.ItemDecoration {

    private StickyHeader stickyHeader;
    private int heightOfHeader;

    public SchedulerItemDecoration(StickyHeader listener) {
        stickyHeader = listener;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        View topChild = parent.getChildAt(0);
        if (topChild == null) {
            return;
        }

        int topChildPosition = parent.getChildAdapterPosition(topChild);
        if (topChildPosition == RecyclerView.NO_POSITION) {
            return;
        }

        int headerPos = stickyHeader.getHeaderPositionForItem(topChildPosition);
        View currentHeader = getHeaderViewForItem(headerPos, parent);
        fixLayoutSize(parent, currentHeader);
        int contactPoint = currentHeader.getBottom();
        View childInContact = getChildInContact(parent, contactPoint, headerPos);

        if (childInContact != null && stickyHeader.isHeader(parent.getChildAdapterPosition(childInContact))) {
            moveHeader(c, currentHeader, childInContact);
            return;
        }

        drawHeader(c, currentHeader);
    }

    private View getHeaderViewForItem(int headerPosition, RecyclerView parent) {
        View header = LayoutInflater.from(parent.getContext()).inflate(stickyHeader.getHeaderLayout(headerPosition), parent, false);
        stickyHeader.bindHeaderData(header, headerPosition);
        return header;
    }

    private void drawHeader(Canvas c, View header) {
        c.save();
        c.translate(0, 0);
        header.draw(c);
        c.restore();
    }

    private void moveHeader(Canvas c, View currentHeader, View nextHeader) {
        c.save();
        c.translate(0, nextHeader.getTop() - currentHeader.getHeight());
        currentHeader.draw(c);
        c.restore();
    }

    private View getChildInContact(RecyclerView parent, int contactPoint, int currentHeaderPos) {
        View childInContact = null;
        for (int i = 0; i < parent.getChildCount(); i++) {
            int heightTolerance = 0;
            View child = parent.getChildAt(i);

            //measure height tolerance with child if child is another header
            if (currentHeaderPos != i) {
                boolean isChildHeader = stickyHeader.isHeader(parent.getChildAdapterPosition(child));
                if (isChildHeader) {
                    heightTolerance = heightOfHeader - child.getHeight();
                }
            }

            //add heightTolerance if child top be in display area
            int childBottomPosition;
            if (child.getTop() > 0) {
                childBottomPosition = child.getBottom() + heightTolerance;
            } else {
                childBottomPosition = child.getBottom();
            }

            if (childBottomPosition > contactPoint) {
                if (child.getTop() <= contactPoint) {
                    // This child overlaps the contactPoint
                    childInContact = child;
                    break;
                }
            }
        }
        return childInContact;
    }


    private void fixLayoutSize(ViewGroup parent, View view) {

        // Specs for parent (RecyclerView)
        int widthSpec = View.MeasureSpec.makeMeasureSpec(parent.getWidth(), View.MeasureSpec.EXACTLY);
        int heightSpec = View.MeasureSpec.makeMeasureSpec(parent.getHeight(), View.MeasureSpec.UNSPECIFIED);

        // Specs for children (headers)
        int childWidthSpec = ViewGroup.getChildMeasureSpec(widthSpec, parent.getPaddingLeft() + parent.getPaddingRight(), view.getLayoutParams().width);
        int childHeightSpec = ViewGroup.getChildMeasureSpec(heightSpec, parent.getPaddingTop() + parent.getPaddingBottom(), view.getLayoutParams().height);

        view.measure(childWidthSpec, childHeightSpec);

        heightOfHeader = view.getMeasuredHeight();
        view.layout(0, 0, view.getMeasuredWidth(), heightOfHeader);
    }
}