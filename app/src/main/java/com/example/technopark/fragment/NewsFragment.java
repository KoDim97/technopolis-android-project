package com.example.technopark.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.example.technopark.R;
import com.example.technopark.adapter.NewsAdapter;
import com.example.technopark.dto.News;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {

    public NewsFragment() {
    }

    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_news, container, false);
        recyclerView = view.findViewById(R.id.activity_news__news_list);
        final RadioGroup radioGroup = view.findViewById(R.id.activity_news__top_bar);
        final NewsAdapter adapter = new NewsAdapter(generateNewsList());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (R.id.activity_news__radio_subs == checkedId) {
                    adapter.onReplace(generateSubsList());
                } else {
                    adapter.onReplace(generateNewsList());
                }
            }
        });
        return view;
    }

    private List<News> generateSubsList() {
        List<News> news = new ArrayList<>();
        news.add(new News(
                "Дмитрий Щитинин",
                "Домашнее задание после второй лекции",
                "Алгоритмы и структуры данных",
                "3 октября 2019 г. 12:25",
                R.drawable.dmitry,
                "1"
        ));
        news.add(new News(
                "Дмитрий Щитинин",
                "Домашнее задание после второй лекции",
                "Алгоритмы и структуры данных",
                "3 октября 2019 г. 12:25",
                R.drawable.dmitry,
                "1"
        ));
        news.add(new News(
                "Дмитрий Щитинин",
                "Домашнее задание после второй лекции",
                "Алгоритмы и структуры данных",
                "3 октября 2019 г. 12:25",
                R.drawable.dmitry,
                "1"
        ));
        news.add(new News(
                "Дмитрий Щитинин",
                "Домашнее задание после второй лекции",
                "Алгоритмы и структуры данных",
                "3 октября 2019 г. 12:25",
                R.drawable.dmitry,
                "1"
        ));
        news.add(new News(
                "Дмитрий Щитинин",
                "Домашнее задание после второй лекции",
                "Алгоритмы и структуры данных",
                "3 октября 2019 г. 12:25",
                R.drawable.dmitry,
                "1"
        ));
        news.add(new News(
                "Дмитрий Щитинин",
                "Домашнее задание после второй лекции",
                "Алгоритмы и структуры данных",
                "3 октября 2019 г. 12:25",
                R.drawable.dmitry,
                "1"
        ));
        news.add(new News(
                "Дмитрий Щитинин",
                "Домашнее задание после второй лекции",
                "Алгоритмы и структуры данных",
                "3 октября 2019 г. 12:25",
                R.drawable.dmitry,
                "1"
        ));
        news.add(new News(
                "Дмитрий Щитинин",
                "Домашнее задание после второй лекции",
                "Алгоритмы и структуры данных",
                "3 октября 2019 г. 12:25",
                R.drawable.dmitry,
                "1"
        ));
        news.add(new News(
                "Дмитрий Щитинин",
                "Домашнее задание после второй лекции",
                "Алгоритмы и структуры данных",
                "3 октября 2019 г. 12:25",
                R.drawable.dmitry,
                "1"
        ));
        news.add(new News(
                "Дмитрий Щитинин",
                "Домашнее задание после второй лекции",
                "Алгоритмы и структуры данных",
                "3 октября 2019 г. 12:25",
                R.drawable.dmitry,
                "1"
        ));

        return news;
    }

    private List<News> generateNewsList() {
        List<News> news = new ArrayList<>();
        news.add(new News(
                "Иван Метелёв",
                "Упражнения после лекции",
                "Frontend-разработка 2019",
                "21 октября 2019 г. 12:34",
                R.drawable.ivan,
                "2"
        ));
        news.add(new News(
                "Филипп Федчин",
                "День открытых дверей Технополиса",
                "Мероприятия",
                "2 декабря 2019 г. 2:56",
                R.drawable.filipp,
                "0"
        ));

        return news;
    }

}
