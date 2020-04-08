package com.example.technopark.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.technopark.R;
import com.example.technopark.dto.SchedulerItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SchedulerItemAdapter extends RecyclerView.Adapter<SchedulerItemAdapter.SchedulerItemViewHolder> {

    private List<SchedulerItem> schedulerItemsList = new ArrayList<>();

    class SchedulerItemViewHolder extends RecyclerView.ViewHolder {

        private TextView dateTextView;
        private TextView startTimeTextView;
        private TextView endTimeTextView;
        private TextView lessonTypeTextView;
        private TextView subjectNameTextView;
        private TextView lessonNameTextView;
        private TextView lessonLocationTextView;

        public SchedulerItemViewHolder(@NonNull View itemView) {
            super(itemView);

            dateTextView = itemView.findViewById(R.id.scheduler_item_date_text_view);
            startTimeTextView = itemView.findViewById(R.id.scheduler_item_start_time);
            endTimeTextView = itemView.findViewById(R.id.scheduler_item_end_time);
            lessonTypeTextView = itemView.findViewById(R.id.scheduler_item_lesson_type);
            subjectNameTextView = itemView.findViewById(R.id.scheduler_item_subject_name);
            lessonNameTextView = itemView.findViewById(R.id.scheduler_item_lesson_name);
            lessonLocationTextView = itemView.findViewById(R.id.scheduler_item_lesson_location);
        }

        public void bind(SchedulerItem schedulerItem) {
            dateTextView.setText(schedulerItem.getDate());
            startTimeTextView.setText(schedulerItem.getStartTime());
            endTimeTextView.setText(schedulerItem.getEndTime());
            subjectNameTextView.setText(schedulerItem.getSubjectName());
            lessonNameTextView.setText(schedulerItem.getLessonName());
            lessonLocationTextView.setText(schedulerItem.getLocation());

            String lessonType = schedulerItem.getLessonType();
            lessonTypeTextView.setText(lessonType.length() > 8 ? lessonType.substring(0, 8) + "..." : lessonType);
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
