package com.example.technopark.fragment;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.technopark.BaseActivity;
import com.example.technopark.R;
import com.example.technopark.dto.PersonItem;
import com.example.technopark.adapter.GroupListAdapter;

import java.util.ArrayList;
import java.util.List;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class GroupListFragment extends Fragment implements GroupListAdapter.Listener {

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
    private Button clearButton;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.grouplist_fragment, container, false);
        members = generatedGroupList();
        recyclerView = view.findViewById(R.id.grouplist_fragment__rv);
        adapter = new GroupListAdapter(members, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider));
        recyclerView.addItemDecoration(itemDecorator);
        OverScrollDecoratorHelper.setUpOverScroll(recyclerView, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);


        cancel = view.findViewById(R.id.grouplist_fragment__cancel);
        searchField = view.findViewById(R.id.grouplist_fragment__searchfield);
        clearButton = view.findViewById(R.id.grouplist_fragment__clearbutton);

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.updateList(members);
                searchField.setText("");
            }
        });

        Toolbar toolbar = view.findViewById(R.id.grouplist_fragment__topbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        TextView textView = view.findViewById(R.id.grouplist_fragment__canceltext);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(getActivity(), searchField);
                searchField.setText("");
                cancel.setVisibility(View.GONE);
            }
        });


        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                final String str = s.toString();
                if (str.length() == 0) {
                    clearButton.setVisibility(View.INVISIBLE);
                    adapter.updateList(members);
                    return;
                }
                clearButton.setVisibility(View.VISIBLE);
                cancel.setVisibility(View.VISIBLE);
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

    public static void hideKeyboard(Activity activity, EditText editText) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        editText.clearFocus();
    }

    @Override
    public void onClick(PersonItem person) {
        ProfileFragment profileFragment = new ProfileFragment();
        profileFragment.setArguments(new Bundle());
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_content, profileFragment, "findThisFragment")
                .addToBackStack(null)
                .commit();
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
