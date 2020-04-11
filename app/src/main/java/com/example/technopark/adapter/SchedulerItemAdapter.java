package com.example.technopark.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.technopark.R;
import com.example.technopark.dto.SchedulerItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SchedulerItemAdapter extends RecyclerView.Adapter<SchedulerItemAdapter.SchedulerItemViewHolder> {

    private static final String RESPONSE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String WEEK_DAY_NUMBER_FORMAT = "EEEE, d MMMM";
    private static final String LESSON_TIME_FORMAT = "HH:mm";

    private List<SchedulerItem> schedulerItemsList = new ArrayList<>();
    private View currParent;

    class SchedulerItemViewHolder extends RecyclerView.ViewHolder {

        private TextView dateTextView;
        private TextView startTimeTextView;
        private TextView endTimeTextView;
        private TextView lessonTypeTextView;
        private TextView subjectNameTextView;
        private TextView lessonNameTextView;
        private TextView lessonLocationTextView;
        private Button onActionButton;

        public SchedulerItemViewHolder(@NonNull View itemView) {
            super(itemView);

            currParent = itemView;

            dateTextView = itemView.findViewById(R.id.scheduler_item_date_text_view);
            startTimeTextView = itemView.findViewById(R.id.scheduler_item_start_time);
            endTimeTextView = itemView.findViewById(R.id.scheduler_item_end_time);
            lessonTypeTextView = itemView.findViewById(R.id.scheduler_item_lesson_type);
            subjectNameTextView = itemView.findViewById(R.id.scheduler_item_subject_name);
            lessonNameTextView = itemView.findViewById(R.id.scheduler_item_lesson_name);
            lessonLocationTextView = itemView.findViewById(R.id.scheduler_item_lesson_location);
            onActionButton = itemView.findViewById(R.id.scheduler_item_button);
        }

        public void bind(SchedulerItem schedulerItem) {
            dateTextView.setText(schedulerItem.getDate());
//            startTimeTextView.setText(schedulerItem.getStartTime());
//            endTimeTextView.setText(schedulerItem.getEndTime());
            subjectNameTextView.setText(schedulerItem.getSubjectName());
            lessonNameTextView.setText(schedulerItem.getLessonName());
            lessonLocationTextView.setText(schedulerItem.getLocation());

            String lessonType = schedulerItem.getLessonType();
            lessonTypeTextView.setText(lessonType.length() > 8 ? lessonType.substring(0, 8) + "..." : lessonType);
            bindActionButton(schedulerItem);
            bindDate(schedulerItem);
            bindLessonTime(startTimeTextView, schedulerItem.getStartTime());
            bindLessonTime(endTimeTextView, schedulerItem.getEndTime());
//            onActionButton.setVisibility(View.INVISIBLE);
        }

        private void bindDate(SchedulerItem schedulerItem) {
            String rawDate = schedulerItem.getDate();
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

        private void bindActionButton(SchedulerItem schedulerItem) {
            Date currentDate = Calendar.getInstance().getTime();
            onActionButton.setText("123");
            onActionButton.setBackground(ContextCompat.getDrawable(currParent.getContext(),R.drawable.scheduler_on_rate_element));
            onActionButton.setTextColor(ContextCompat.getColor(currParent.getContext(),R.color.colorWhite));
        }

        private String processDate(String formattedDate) {
            return formattedDate.substring(0, 1).toUpperCase().concat(formattedDate.substring(1));
        }
    }

    public void setItems(Collection<SchedulerItem> schedulerItems) {
        schedulerItemsList.addAll(schedulerItems);
        notifyDataSetChanged();
    }

    public void clearItems() {
        schedulerItemsList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SchedulerItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.scheduler_item_view, parent, false);
        return new SchedulerItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SchedulerItemViewHolder holder, int position) {
        holder.bind(schedulerItemsList.get(position));
    }

    @Override
    public int getItemCount() {
        return schedulerItemsList.size();
    }
}
