package com.example.technopark;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.technopark.dto.PersonItem;
import com.example.technopark.adapter.GroupListAdapter;
import java.util.ArrayList;
import java.util.List;

public class GroupListFragment extends Fragment {

    public GroupListFragment(){
    }

    public static GroupListFragment newInstance() {
        return new GroupListFragment();
    }

    private RecyclerView recyclerView;
    private GroupListAdapter adapter;
    private List<PersonItem> members;
    private TextView cancel;
    private EditText searchField;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((BaseActivity)getActivity()).setBarVisible(View.GONE);
        View view = inflater.inflate(R.layout.activity_grouplist, container, false);
        members = generatedGroupList();
        recyclerView = view.findViewById(R.id.activity_grouplist__rv);
        adapter = new GroupListAdapter(members);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider));
        recyclerView.addItemDecoration(itemDecorator);

        cancel = view.findViewById(R.id.activity_grouplist__cancel);
        searchField = view.findViewById(R.id.activity_grouplist__searchfield);
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
                        adapter.updateList(members);
                        searchField.setText("");
                        return false;
                    }
                }
                return false;
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(getActivity());
                searchField.setText("");
                cancel.setVisibility(View.GONE);
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
                    adapter.updateList(members);
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
        return view;
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

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
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
