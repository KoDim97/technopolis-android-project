package com.example.technopark.adapter;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.technopark.R;
import com.example.technopark.adapter.stickyHeader.StickyHeader;
import com.example.technopark.dto.SchedulerItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static androidx.core.content.ContextCompat.startActivity;

public class SchedulerItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements StickyHeader {

    private static final String RESPONSE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String WEEK_DAY_NUMBER_FORMAT = "EEEE, d MMMM";
    private static final String LESSON_TIME_FORMAT = "HH:mm";

    private List<SchedulerItem> schedulerItemsList = new ArrayList<>();
    private View currParent;

    class SchedulerItemHeaderViewHolder extends RecyclerView.ViewHolder {

        private TextView dateTextView;

        public SchedulerItemHeaderViewHolder(@NonNull View itemView) {
            super(itemView);

            dateTextView = itemView.findViewById(R.id.scheduler_item_header);

        }

        private void bind(String date) {
            bindDate(dateTextView, date);
        }

    }

    class SchedulerItemViewHolder extends RecyclerView.ViewHolder {

        private TextView startTimeTextView;
        private TextView endTimeTextView;
        private TextView lessonTypeTextView;
        private TextView subjectNameTextView;
        private TextView lessonNameTextView;
        private TextView lessonLocationTextView;
        private Button onActionButton;
        private ImageView acceptReportImageView;

        public SchedulerItemViewHolder(@NonNull View itemView) {
            super(itemView);

            currParent = itemView;

            startTimeTextView = itemView.findViewById(R.id.scheduler_item_start_time);
            endTimeTextView = itemView.findViewById(R.id.scheduler_item_end_time);
            lessonTypeTextView = itemView.findViewById(R.id.scheduler_item_lesson_type);
            subjectNameTextView = itemView.findViewById(R.id.scheduler_item_subject_name);
            lessonNameTextView = itemView.findViewById(R.id.scheduler_item_lesson_name);
            lessonLocationTextView = itemView.findViewById(R.id.scheduler_item_lesson_location);
            onActionButton = itemView.findViewById(R.id.scheduler_item_button);
            acceptReportImageView = itemView.findViewById(R.id.scheduler_accept_report_icon);
        }

        public void bind(SchedulerItem schedulerItem) {
            subjectNameTextView.setText(schedulerItem.getSubjectName());
            lessonNameTextView.setText(schedulerItem.getLessonName());
            lessonLocationTextView.setText(schedulerItem.getLocation());

            String lessonType = schedulerItem.getLessonType();
            lessonTypeTextView.setText(lessonType.length() > 8 ? lessonType.substring(0, 8) + "..." : lessonType);
            bindActionButton(schedulerItem);
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

        private void bindActionButton(final SchedulerItem schedulerItem) {
            if (schedulerItem.getFeedbackUrl() != null) {
                setOnFeedback(schedulerItem);
            } else if (schedulerItem.isAttended()) {
                setOnIsAttended();
            }
            else  if (schedulerItem.isCheckInOpen()) {
                setOnIsCheckedInOpen(schedulerItem);
            } else {
                onActionButton.setVisibility(View.INVISIBLE);
                acceptReportImageView.setVisibility(View.INVISIBLE);
            }
        }

        private void setOnFeedback(final SchedulerItem schedulerItem) {
            setButtonCharacteristics(
                    "Оценить",
                    ContextCompat.getDrawable(currParent.getContext(), R.drawable.scheduler_on_rate_element),
                    ContextCompat.getColor(currParent.getContext(), R.color.colorWhite)
            );
            onActionButton.setVisibility(View.VISIBLE);
            acceptReportImageView.setVisibility(View.INVISIBLE);
            onActionButton.setOnClickListener(new View.OnClickListener() {
                //need to use schedulerItem.getFeedbackUrl() instead of https://www.google.ru/
                //when API will connected to the App
                @Override
                public void onClick(View v) {
                    Intent viewIntent = new Intent(
                            "android.intent.action.VIEW",
                            Uri.parse(schedulerItem.getFeedbackUrl() == null
                                    ? "https://www.google.ru/"
                                    : schedulerItem.getFeedbackUrl())
                    );
                    startActivity(currParent.getContext(), viewIntent, new Bundle());
                    setOnIsAttended();
                }
            });
        }

        private void setOnIsAttended() {
            onActionButton.setVisibility(View.INVISIBLE);
            acceptReportImageView.setVisibility(View.VISIBLE);
        }

        private void setOnIsCheckedInOpen(final SchedulerItem schedulerItem) {
            setButtonCharacteristics(
                    "Отметиться",
                    ContextCompat.getDrawable(currParent.getContext(), R.drawable.scheduler_on_present_element),
                    ContextCompat.getColor(currParent.getContext(), R.color.colorBlue)
            );
            onActionButton.setVisibility(View.VISIBLE);
            acceptReportImageView.setVisibility(View.INVISIBLE);
            onActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    schedulerItem.setAttended(true);
                    setOnFeedback(schedulerItem);
                }
            });
        }

        private void setButtonCharacteristics(String buttonText, Drawable background, int textColor) {
            onActionButton.setText(buttonText);
            onActionButton.setBackground(background);
            onActionButton.setTextColor(textColor);
        }
    }

    public void setItems(Collection<SchedulerItem> schedulerItems) {
        schedulerItemsList.addAll(schedulerItems);
        notifyDataSetChanged();
    }

    public void updateItems(Collection<SchedulerItem> schedulerItems) {
        schedulerItemsList.clear();
        schedulerItemsList.addAll(schedulerItems);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case 1:
                View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.scheduler_item_view, parent, false);
                return new SchedulerItemViewHolder(view1);
            case 0:
                View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.scheduler_item_header_view, parent, false);
                return new SchedulerItemHeaderViewHolder(view2);
            default:
                throw new RuntimeException("Unexpected type of view");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case 1:
                SchedulerItemViewHolder schedulerItemViewHolder = (SchedulerItemViewHolder) holder;
                schedulerItemViewHolder.bind(schedulerItemsList.get(position / 2));
                break;
            case 0:
                SchedulerItemHeaderViewHolder schedulerItemHeaderViewHolder = (SchedulerItemHeaderViewHolder) holder;
                schedulerItemHeaderViewHolder.bind(schedulerItemsList.get(position / 2).getDate());
                break;
        }
    }



    @Override
    public int getItemCount() {
        return schedulerItemsList.size() * 2;
    }

    //Sticky header interface
    @Override
    public int getHeaderPositionForItem(int itemPosition) {
        return itemPosition % 2 == 0 ? itemPosition : itemPosition - 1;
    }

    @Override
    public int getHeaderLayout(int headerPosition) {
        return R.layout.scheduler_item_header_view;
    }

    @Override
    public void bindHeaderData(View header, int headerPosition) {
        TextView dateText = header.findViewById(R.id.scheduler_item_header);
        bindDate(dateText, schedulerItemsList.get(headerPosition / 2).getDate());
    }

    @Override
    public boolean isHeader(int position) {
        return position % 2 == 0;
    }

    private void bindDate(TextView dateTextView, String rawDate) {
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
