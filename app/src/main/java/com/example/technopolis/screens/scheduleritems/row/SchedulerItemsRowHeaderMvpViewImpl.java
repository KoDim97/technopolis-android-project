package com.example.technopolis.screens.scheduleritems.row;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.technopolis.R;
import com.example.technopolis.screens.common.mvp.MvpViewBase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SchedulerItemsRowHeaderMvpViewImpl extends MvpViewBase implements SchedulerItemsRowHeaderMvpView {

    private static final String RESPONSE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String WEEK_DAY_NUMBER_FORMAT = "EEEE, d MMMM";

    private TextView dateTextView;
    private String date;

    public SchedulerItemsRowHeaderMvpViewImpl(LayoutInflater layoutInflater, ViewGroup parent) {

        setRootView(layoutInflater.inflate(R.layout.scheduler_item_header_view, parent, false));

        dateTextView = findViewById(R.id.scheduler_item_header);

    }

    @Override
    public void bindData(String date) {
        this.date = date;
        bindDate(date);
    }

    private void bindDate(String rawDate) {
        SimpleDateFormat utcFormat = new SimpleDateFormat(RESPONSE_FORMAT, Locale.ROOT);
        Locale locale = new Locale("ru");
        SimpleDateFormat displayedFormat = new SimpleDateFormat(WEEK_DAY_NUMBER_FORMAT, locale);
        try {
            Date date = utcFormat.parse(rawDate);
            dateTextView.setText(processDate(displayedFormat.format(date)));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private String processDate(String formattedDate) {
        return formattedDate.substring(0, 1).toUpperCase().concat(formattedDate.substring(1));
    }
}
