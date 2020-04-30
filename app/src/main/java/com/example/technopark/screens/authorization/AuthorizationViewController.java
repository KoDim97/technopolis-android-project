package com.example.technopark.screens.authorization;


import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.viewpager.widget.ViewPager;

import com.example.technopark.BaseActivity;
import com.example.technopark.R;
import com.example.technopark.adapter.MyPager;
import com.example.technopark.screens.common.nav.ScreenNavigator;
import com.example.technopark.user.service.AuthService;
import com.google.android.material.tabs.TabLayout;
import com.example.technopark.App;

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
    private final AuthService authService;
    private Thread thread;
    private String park = "https://park.mail.ru";
    private String sphere = "https://sphere.mail.ru";
    private String track = "https://track.mail.ru";
    private String polis = "https://polis.mail.ru";
    private String technoatom = "https://technoatom.mail.ru";
    private String vgu = "https://vgu.sphere.mail.ru";
    private String pgu = "https://pgu.sphere.mail.ru";
    private String data = "https://data.mail.ru";




    public AuthorizationViewController(View rootView, BaseActivity activity){
        screenNavigator=activity.getScreenNavigator();
        myPager = new MyPager(activity,activity);
        viewPager = rootView.findViewById(R.id.view_pager);
        viewPager.setAdapter(myPager);
        mTabLayout = (TabLayout) rootView.findViewById(R.id.tablayout);
        mTabLayout.setupWithViewPager(viewPager);
        prevButton=(ImageButton) rootView.findViewById(R.id.prev_button);
        nextButton=(ImageButton) rootView.findViewById(R.id.next_button);
        enterButton=(Button) rootView.findViewById(R.id.enter);
        loginEditText=(EditText) rootView.findViewById(R.id.Login);
        passwordEditText=(EditText) rootView.findViewById(R.id.Password);
        App app = (App) activity.getApplication();
        assert app != null;
        authService = app.provideAuthService();
        loginEditTextSettings(loginEditText);
        passwordEditTextSettings(passwordEditText);
        viewPagerSettings(viewPager,app);
        btnSettings(activity);
        test();
    }


    private void test(){
        loginEditText.setText("");
        passwordEditText.setText("");
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
                if(s.length()!=0){
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

    private void setProject(int position,App app){
        switch (position) {
            case 0:
                app.provideMailApi().setProjectUrl(park);
                return;
            case 1:
                app.provideMailApi().setProjectUrl(polis);
                return;
            case 2:
                app.provideMailApi().setProjectUrl(technoatom);
                return;
            case 3:
                app.provideMailApi().setProjectUrl(sphere);
                return;
            case 4:
                app.provideMailApi().setProjectUrl(track);
                return;
            case 5:
                app.provideMailApi().setProjectUrl(pgu);
                return;
            case 6:
                app.provideMailApi().setProjectUrl(vgu);
                return;
            default:
                app.provideMailApi().setProjectUrl(park);
        }
    }


    private void viewPagerSettings(ViewPager viewPager, final App app){
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int totalItems = myPager.getCount();
                setProject(position,app);
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
                thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (authService.CheckAuth(loginEditText.getText().toString(), passwordEditText.getText().toString())) {
                            screenNavigator.changeAuthorized(true);
                        } else {
                            
                        }
                    }
                });
                thread.start();
            }
        });
    }


}
