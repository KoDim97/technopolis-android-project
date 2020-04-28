package com.example.technopark.screens.authorization;


import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.viewpager.widget.ViewPager;

import com.example.technopark.BaseActivity;
import com.example.technopark.R;
import com.example.technopark.adapter.MyPager;
import com.example.technopark.screens.common.nav.ScreenNavigator;
import com.google.android.material.tabs.TabLayout;

import static android.view.KeyEvent.KEYCODE_ENTER;

public class AuthorizationViewController  {
    private ScreenNavigator screenNavigator;
    private ImageButton prevButton;
    private ImageButton nextButton;
    private ViewPager viewPager;
    private MyPager myPager;
    private TabLayout mTabLayout;
    private EditText loginEditText;
    private EditText passwordEditText;
    private Button enterButton;
    private boolean[] enableEnter=new boolean[2];
    private String login;
    private String password;

    public AuthorizationViewController(View rootView, BaseActivity activity){
        screenNavigator=activity.getScreenNavigator();
        myPager = new MyPager(activity);
        viewPager = rootView.findViewById(R.id.view_pager);
        viewPager.setAdapter(myPager);
        mTabLayout = (TabLayout) rootView.findViewById(R.id.tablayout);
        mTabLayout.setupWithViewPager(viewPager);
        prevButton=(ImageButton) rootView.findViewById(R.id.prev_button);
        nextButton=(ImageButton) rootView.findViewById(R.id.next_button);
        enterButton=(Button) rootView.findViewById(R.id.enter);
        loginEditText=(EditText) rootView.findViewById(R.id.Login);
        passwordEditText=(EditText) rootView.findViewById(R.id.Password);
        loginEditTextSettings(loginEditText);
        passwordEditTextSettings(passwordEditText);
        viewPagerSettings(viewPager);
        btnSettings(activity);
    }



    private void changeEnableEnter(){
        if(enableEnter[0]&&enableEnter[1]){
            enterButton.setEnabled(true);
        }else
            enterButton.setEnabled(false);
    }


    private void loginEditTextSettings(EditText editText){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //  вызывается,когда добавляется каждый символ
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //  вызывается перед вводом
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()!=0){
                    enableEnter[0]=true;
                }else enableEnter[0]=false;
                changeEnableEnter();
            }
        });
        editText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                passwordEditText.requestFocus();
                return true;
            }
        });
    }
    private void passwordEditTextSettings(EditText editText){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //  вызывается,когда добавляется каждый символ
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //  вызывается перед вводом
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()!=1){
                    enableEnter[1]=true;
                }else enableEnter[1]=false;
                changeEnableEnter();
            }
        });
        editText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                boolean consumed = false;
                if (keyCode == KEYCODE_ENTER) {
                    if(enterButton.isEnabled()){
                        enterButton.performClick();
                    }
                    consumed = true;
                }
                return consumed;
            }
        });
    }

    private void viewPagerSettings(ViewPager viewPager){
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int totalItems = myPager.getCount();
                if(position==0){
                    prevButton.setVisibility(View.INVISIBLE);
                }else if(position==totalItems-1){
                    nextButton.setVisibility(View.INVISIBLE);
                }else {
                    prevButton.setVisibility(View.VISIBLE);
                    nextButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void btnSettings(final BaseActivity activity){
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = viewPager.getCurrentItem();
                int totalItems = myPager.getCount();
                if(current < totalItems - 1) {
                    viewPager.setCurrentItem(current + 1, true);
                }
            }
        });
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = viewPager.getCurrentItem();
                if(current != 0) {
                    viewPager.setCurrentItem(current - 1, true);
                }
            }
        });
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login=loginEditText.getText().toString();
                password=passwordEditText.getText().toString();
                screenNavigator.changeAuthorized(true);
            }
        });
    }


}