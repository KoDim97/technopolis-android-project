package com.example.technopark.grouplist;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.technopark.R;

import java.util.ArrayList;
import java.util.List;

public class GroupListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grouplist);
        recyclerView = findViewById(R.id.activity_grouplist__rv);
        PersonAdapter adapter = new PersonAdapter(generatedGroupList());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration itemDecorator = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider));
        recyclerView.addItemDecoration(itemDecorator);
    }

    private List<Person> generatedGroupList(){
        List<Person> groups = new ArrayList<>();
        groups.add(new Person("Филипп Федчин", R.drawable.img1, 0));
        groups.add(new Person("Дмитрий Махнев", R.drawable.img0, 1));
        groups.add(new Person("Тимур Насрединов", R.drawable.img2, 2));
        groups.add(new Person("Сергей Михалев", R.drawable.img3, 3));
        groups.add(new Person("Андрей Фильчаков", R.drawable.img4, 4));
        groups.add(new Person("Дмитрий Щитинин", R.drawable.img5, 5));
        groups.add(new Person("Антон Ламтев", R.drawable.img6, 6));
        groups.add(new Person("Иван Метелёв", R.drawable.img7, 7));
        groups.add(new Person("Александр Галкин", R.drawable.img8, 8));
        groups.add(new Person("Михаил Марюфич", R.drawable.img9, 9));
        groups.add(new Person("Александр Грицук", R.drawable.img10, 10));
        return groups;
    }
}
