package com.example.technopark.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.technopark.R;
import com.example.technopark.dto.News;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> {

    private final List<News> news;

    public NewsAdapter(List<News> news) {
        this.news = news;
    }


    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        return new NewsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int position) {
        holder.bind(news.get(position));
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    public void onReplace(List<News> update) {
        news.clear();
        news.addAll(update);

        notifyDataSetChanged();
    }

    public class NewsHolder extends RecyclerView.ViewHolder {

        private TextView tvName;
        private TextView tvTitle;
        private TextView tvSection;
        private TextView tvCommentNum;
        private TextView tvDate;
        private ImageView ivUserpic;

        private NewsHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.news_item__name);
            tvTitle = itemView.findViewById(R.id.news_item__title);
            tvSection = itemView.findViewById(R.id.news_item__section);
            tvCommentNum = itemView.findViewById(R.id.news_item__comment_num);
            tvDate = itemView.findViewById(R.id.news_item__date);
            ivUserpic = itemView.findViewById(R.id.news_item__image);
        }

        private void bind(News news) {
            ivUserpic.setImageResource(news.getUserpic());
            tvName.setText(news.getName());
            tvTitle.setText(news.getTitle());
            tvSection.setText(news.getSection());
            tvCommentNum.setText(news.getComments_number());
            tvDate.setText(news.getDate());
        }
    }
}
