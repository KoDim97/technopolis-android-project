package com.example.technopark;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_news);
        recyclerView = findViewById(R.id.activity_news__news_list);

        final RadioGroup radioGroup = findViewById(R.id.activity_news__top_bar);
        NewsAdapter adapter = new NewsAdapter(generateNewsList());

//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//
//                @Override
//                public void onCheckedChanged(RadioGroup group, int checkedId) {
//                    if (checkedId == R.id.activity_news__radio_main) {
//                        adapter = generateNewsList();
//                    }
//                }
//        });


        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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

//    private List<News> generateSubsList() {
//        List<News> news = new ArrayList<>();
//        news.add(new News(
//                "Дмитрий Щитинин",
//                "Домашнее задание после второй лекции",
//                "Алгоритмы и структуры данных",
//                "3 октября 2019 г. 12:25",
//                R.drawable.dmitry,
//                "1"
//        ));
//        news.add(new News(
//                "Дмитрий Щитинин",
//                "Домашнее задание после второй лекции",
//                "Алгоритмы и структуры данных",
//                "3 октября 2019 г. 12:25",
//                R.drawable.dmitry,
//                "1"
//        ));
//        news.add(new News(
//                "Дмитрий Щитинин",
//                "Домашнее задание после второй лекции",
//                "Алгоритмы и структуры данных",
//                "3 октября 2019 г. 12:25",
//                R.drawable.dmitry,
//                "1"
//        ));
//        news.add(new News(
//                "Дмитрий Щитинин",
//                "Домашнее задание после второй лекции",
//                "Алгоритмы и структуры данных",
//                "3 октября 2019 г. 12:25",
//                R.drawable.dmitry,
//                "1"
//        ));
//        news.add(new News(
//                "Дмитрий Щитинин",
//                "Домашнее задание после второй лекции",
//                "Алгоритмы и структуры данных",
//                "3 октября 2019 г. 12:25",
//                R.drawable.dmitry,
//                "1"
//        ));
//
//        return news;
//    }

}
