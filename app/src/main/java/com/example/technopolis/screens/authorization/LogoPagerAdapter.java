package com.example.technopolis.screens.authorization;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.technopolis.BaseActivity;
import com.example.technopolis.R;

public class LogoPagerAdapter extends androidx.viewpager.widget.PagerAdapter {
    private Context context;
    private int num = 7;


    public LogoPagerAdapter(Context context, BaseActivity activity) {
        this.context = context;

    }

    /*
    This callback is responsible for creating a page. We inflate the layout and set the drawable
    to the ImageView based on the position. In the end we add the inflated layout to the parent
    container .This method returns an object key to identify the page view, but in this example page view
    itself acts as the object key
    */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.pager_item, null);
        ImageView imageView = view.findViewById(R.id.image);
        imageView.setImageDrawable(context.getResources().getDrawable(getImageAt(position)));
        container.addView(view);
        return view;
    }

    /*
    This callback is responsible for destroying a page. Since we are using view only as the
    object key we just directly remove the view from parent container
    */
    @Override
    public void destroyItem(ViewGroup container, int position, Object view) {
        container.removeView((View) view);
    }

    /*
    Returns the count of the total pages
    */
    @Override
    public int getCount() {
        return 7;
    }

    /*
    Used to determine whether the page view is associated with object key returned by instantiateItem.
    Since here view only is the key we return view==object
    */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return object == view;
    }

    private int getImageAt(int position) {
        switch (position) {
            case 0:
                return R.drawable.logo0;
            case 1:
                return R.drawable.logo1;
            case 2:
                return R.drawable.logo2;
            case 3:
                return R.drawable.logo3;
            case 4:
                return R.drawable.logo4;
            case 5:
                return R.drawable.logo5;
            case 6:
                return R.drawable.logo6;
            default:
                return R.drawable.logo0;
        }
    }

}
