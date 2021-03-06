package com.example.technopolis.screens.scheduleritems.row;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.technopolis.R;
import com.example.technopolis.scheduler.model.SchedulerItem;
import com.example.technopolis.screens.common.mvp.MvpViewObservableBase;
import com.example.technopolis.screens.scheduleritems.IsOnlineSupplier;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SchedulerItemsRowMvpViewImpl extends MvpViewObservableBase implements SchedulerItemsRowMvpView {

    private static final String RESPONSE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String LESSON_TIME_FORMAT = "HH:mm";

    private TextView startTimeTextView;
    private TextView endTimeTextView;
    private TextView lessonTypeTextView;
    private TextView subjectNameTextView;
    private TextView lessonNameTextView;
    private TextView lessonLocationTextView;
    private Button onActionButton;
    private ImageView acceptReportImageView;

    public SchedulerItemsRowMvpViewImpl(LayoutInflater layoutInflater, ViewGroup parent) {

        setRootView(layoutInflater.inflate(R.layout.scheduler_item_view, parent, false));

        startTimeTextView = findViewById(R.id.scheduler_item_start_time);
        endTimeTextView = findViewById(R.id.scheduler_item_end_time);
        lessonTypeTextView = findViewById(R.id.scheduler_item_lesson_type);
        subjectNameTextView = findViewById(R.id.scheduler_item_subject_name);
        lessonNameTextView = findViewById(R.id.scheduler_item_lesson_name);
        lessonLocationTextView = findViewById(R.id.scheduler_item_lesson_location);
        onActionButton = findViewById(R.id.scheduler_item_button);
        acceptReportImageView = findViewById(R.id.scheduler_accept_report_icon);
    }


    @Override
    public void bindData(SchedulerItem schedulerItem, View.OnClickListener listener, IsOnlineSupplier supplier) {
        subjectNameTextView.setText(schedulerItem.getSubjectName());
        lessonNameTextView.setText(schedulerItem.getLessonName());
        lessonLocationTextView.setText(schedulerItem.getLocation());

        String lessonType = schedulerItem.getLessonType();
        lessonTypeTextView.setText(lessonType.length() > 8 ? lessonType.substring(0, 8) + "..." : lessonType);
        bindActionButton(schedulerItem, listener, supplier);
        bindLessonTime(startTimeTextView, schedulerItem.getStartTime());
        bindLessonTime(endTimeTextView, schedulerItem.getEndTime());
    }

    private void bindLessonTime(TextView time, String rawDate) {
        SimpleDateFormat utcFormat = new SimpleDateFormat(RESPONSE_FORMAT, Locale.ROOT);
        Locale locale = new Locale("ru");
        SimpleDateFormat displayedFormat = new SimpleDateFormat(LESSON_TIME_FORMAT, locale);
        try {
            Date date = utcFormat.parse(rawDate);
            time.setText(displayedFormat.format(date));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private void bindActionButton(final SchedulerItem schedulerItem, final View.OnClickListener listener, final IsOnlineSupplier supplier) {
        if (!schedulerItem.getFeedbackUrl().equals("null")) {
            setOnFeedback(supplier);
        } else if (schedulerItem.isAttended()) {
            setOnIsAttended();
        } else if (schedulerItem.isCheckInOpen()) {
            setOnIsCheckedInOpen(listener);
        } else {
            onActionButton.setVisibility(View.INVISIBLE);
            acceptReportImageView.setVisibility(View.INVISIBLE);
        }
    }

    private void setOnFeedback(final IsOnlineSupplier supplier) {
        setButtonCharacteristics(
                "Оценить",
                ContextCompat.getDrawable(getContext(), R.drawable.scheduler_on_rate_element),
                ContextCompat.getColor(getContext(), R.color.colorWhite)
        );
        onActionButton.setVisibility(View.VISIBLE);
        acceptReportImageView.setVisibility(View.INVISIBLE);
        onActionButton.setOnClickListener(v -> supplier.openOnFeedbackView());
    }

    private void setOnIsAttended() {
        onActionButton.setVisibility(View.INVISIBLE);
        acceptReportImageView.setVisibility(View.VISIBLE);
    }

    private void setOnIsCheckedInOpen(final View.OnClickListener listener) {
        setButtonCharacteristics(
                "Отметиться",
                ContextCompat.getDrawable(getContext(), R.drawable.scheduler_on_present_element),
                ContextCompat.getColor(getContext(), R.color.colorBlue)
        );
        onActionButton.setVisibility(View.VISIBLE);
        acceptReportImageView.setVisibility(View.INVISIBLE);
        onActionButton.setOnClickListener(listener);
    }

    private void setButtonCharacteristics(String buttonText, Drawable background, int textColor) {
        onActionButton.setText(buttonText);
        onActionButton.setBackground(background);
        onActionButton.setTextColor(textColor);
    }
}
