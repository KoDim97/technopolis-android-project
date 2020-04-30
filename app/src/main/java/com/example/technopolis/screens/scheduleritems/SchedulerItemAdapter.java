package com.example.technopolis.screens.scheduleritems;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.technopolis.R;
import com.example.technopolis.scheduler.model.SchedulerItem;
import com.example.technopolis.screens.scheduleritems.row.SchedulerItemsRowHeaderMvpView;
import com.example.technopolis.screens.scheduleritems.row.SchedulerItemsRowHeaderMvpViewImpl;
import com.example.technopolis.screens.scheduleritems.row.SchedulerItemsRowMvpView;
import com.example.technopolis.screens.scheduleritems.row.SchedulerItemsRowMvpViewImpl;
import com.example.technopolis.screens.scheduleritems.stickyHeader.StickyHeader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class SchedulerItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements StickyHeader {

    private static final String RESPONSE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String WEEK_DAY_NUMBER_FORMAT = "EEEE, d MMMM";

    class SchedulerItemHeaderViewHolder extends RecyclerView.ViewHolder {

        private SchedulerItemsRowHeaderMvpView schedulerItemsRowHeaderMvpView;

        public SchedulerItemHeaderViewHolder(@NonNull SchedulerItemsRowHeaderMvpView schedulerItemsRowHeaderMvpView) {
            super(schedulerItemsRowHeaderMvpView.getRootView());
            this.schedulerItemsRowHeaderMvpView = schedulerItemsRowHeaderMvpView;
        }
    }

    class SchedulerItemViewHolder extends RecyclerView.ViewHolder {

        private SchedulerItemsRowMvpView schedulerItemsRowMvpView;

        public SchedulerItemViewHolder(@NonNull SchedulerItemsRowMvpView schedulerItemsRowMvpView) {
            super(schedulerItemsRowMvpView.getRootView());
            this.schedulerItemsRowMvpView = schedulerItemsRowMvpView;
        }
    }

    private final List<SchedulerItem> schedulerItemsList = new ArrayList<>();
    private final LayoutInflater layoutInflater;
    private List<View.OnClickListener> listeners;

    public SchedulerItemAdapter(LayoutInflater layoutInflater) {
        this.layoutInflater = layoutInflater;
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

    public void bindData(Collection<SchedulerItem> schedulerItems, List<View.OnClickListener> listeners) {
        this.listeners = listeners;
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
                SchedulerItemsRowMvpView schedulerItemsRowMvpView = new SchedulerItemsRowMvpViewImpl(layoutInflater, parent);
                return new SchedulerItemViewHolder(schedulerItemsRowMvpView);
            case 0:
                SchedulerItemsRowHeaderMvpView schedulerItemsRowHeaderMvpView = new SchedulerItemsRowHeaderMvpViewImpl(layoutInflater, parent);
                return new SchedulerItemHeaderViewHolder(schedulerItemsRowHeaderMvpView);
            default:
                throw new RuntimeException("Unexpected type of view");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case 1:
                SchedulerItemViewHolder schedulerItemViewHolder = (SchedulerItemViewHolder) holder;
                SchedulerItem schedulerItem = schedulerItemsList.get(position / 2);
                View.OnClickListener listener = listeners.get(position / 2);
                schedulerItemViewHolder.schedulerItemsRowMvpView.bindData(schedulerItem, listener);
                break;
            case 0:
                SchedulerItemHeaderViewHolder schedulerItemHeaderViewHolder = (SchedulerItemHeaderViewHolder) holder;
                String date = schedulerItemsList.get(position / 2).getDate();
                schedulerItemHeaderViewHolder.schedulerItemsRowHeaderMvpView.bindData(date);
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
