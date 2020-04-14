package com.example.technopark;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.technopark.dto.PersonItem;
import com.example.technopark.adapter.GroupListAdapter;
import java.util.ArrayList;
import java.util.List;

public class GroupListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private GroupListAdapter adapter;
    private List<PersonItem> members;
    private TextView cancel;
    private EditText searchField;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grouplist);
        members = generatedGroupList();
        recyclerView = findViewById(R.id.activity_grouplist__rv);
        adapter = new GroupListAdapter(members);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration itemDecorator = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider));
        recyclerView.addItemDecoration(itemDecorator);

        cancel = findViewById(R.id.activity_grouplist__cancel);
        searchField = findViewById(R.id.activity_grouplist__searchfield);
        searchField.getCompoundDrawablesRelative()[2].setAlpha(0);

        searchField.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (searchField.getRight() - searchField.getCompoundDrawablesRelative()[DRAWABLE_RIGHT].getBounds().width() - 30)) {
                        cancel.setVisibility(View.GONE);
                        adapter.updateList(members);
                        searchField.setText("");
                        return true;
                    }
                }
                return false;
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchField.getCompoundDrawablesRelative()[2].setAlpha(0);
                cancel.setVisibility(View.GONE);
                adapter.updateList(members);
                searchField.setText("");
            }
        });


        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchField.getCompoundDrawablesRelative()[2].setAlpha(255);
                cancel.setVisibility(View.VISIBLE);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                final String str = s.toString();
                if (str.length() == 0) {
                    searchField.getCompoundDrawablesRelative()[2].setAlpha(0);
                    cancel.setVisibility(View.GONE);
                    return;
                }
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        filter(str);
                    }
                }, 20);
            }
        });
    }

    void filter(String text){
        List<PersonItem> temp = new ArrayList();
        for(PersonItem person: members){
            if(person.name.toLowerCase().contains(text.toLowerCase())){
                temp.add(person);
            }
        }
        adapter.updateList(temp);
    }

    private List<PersonItem> generatedGroupList(){
        List<PersonItem> groups = new ArrayList<>();
        groups.add(new PersonItem("Филипп Федчин", R.drawable.img1, 0));
        groups.add(new PersonItem("Дмитрий Махнев", R.drawable.img0, 1));
        groups.add(new PersonItem("Тимур Насрединов", R.drawable.img2, 2));
        groups.add(new PersonItem("Сергей Михалев", R.drawable.img3, 3));
        groups.add(new PersonItem("Андрей Фильчаков", R.drawable.img4, 4));
        groups.add(new PersonItem("Дмитрий Щитинин", R.drawable.img5, 5));
        groups.add(new PersonItem("Антон Ламтев", R.drawable.img6, 6));
        groups.add(new PersonItem("Иван Метелёв", R.drawable.img7, 7));
        groups.add(new PersonItem("Александр Галкин", R.drawable.img8, 8));
        groups.add(new PersonItem("Михаил Марюфич", R.drawable.img9, 9));
        groups.add(new PersonItem("Александр Грицук", R.drawable.img10, 10));
        return groups;
    }
}
