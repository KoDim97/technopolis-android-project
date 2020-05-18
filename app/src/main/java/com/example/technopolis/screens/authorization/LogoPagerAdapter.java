package com.example.technopolis.screens.authorization;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.technopolis.R;

import java.util.ArrayList;

public class LogoPagerAdapter extends androidx.viewpager.widget.PagerAdapter {
    private final Context context;
    private final ArrayList<Integer> logo;

    LogoPagerAdapter(@NonNull Context context) {
        this.context = context;
        logo = new ArrayList<>();
        logo.add(R.drawable.logo0);
        logo.add(R.drawable.logo1);
        logo.add(R.drawable.logo2);
        logo.add(R.drawable.logo3);
        logo.add(R.drawable.logo4);
        logo.add(R.drawable.logo5);
        logo.add(R.drawable.logo6);
    }

    /*
    This callback is responsible for creating a page. We inflate the layout and set the drawable
    to the ImageView based on the position. In the end we add the inflated layout to the parent
    container .This method returns an object key to identify the page view, but in this example page view
    itself acts as the object key
    */
    @NonNull
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
    public void destroyItem(ViewGroup container, int position, @NonNull Object view) {
        container.removeView((View) view);
    }

    /*
    Returns the count of the total pages
    */
    @Override
    public int getCount() {
        return logo.size();
    }

    /*
    Used to determine whether the page view is associated with object key returned by instantiateItem.
    Since here view only is the key we return view==object
    */
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return object == view;
    }

    private int getImageAt(int position) {
        return position < logo.size() ? logo.get(position) : logo.get(0);
    }

}
