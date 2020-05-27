package com.example.technopolis.screens.authorization;


import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.example.technopolis.BaseActivity;
import com.example.technopolis.R;
import com.google.android.material.tabs.TabLayout;

import static android.view.KeyEvent.KEYCODE_ENTER;

class AuthorizationViewInitializer {
    private final ImageButton prevButton;
    private final ImageButton nextButton;
    private final ViewPager viewPager;
    private final LogoPagerAdapter logoPagerAdapter;
    private final EditText loginEditText;
    private final EditText passwordEditText;
    private final Button enterButton;
    private final boolean[] enableEnter = new boolean[2];
    private final AuthorizationViewController controller;
    private boolean authorized = false;

    /**
     * Handler of view's events
     */
    AuthorizationViewInitializer(@NonNull final View rootView, @NonNull final BaseActivity activity) {
        controller = new AuthorizationViewControllerImpl(activity);
        logoPagerAdapter = new LogoPagerAdapter(activity);
        viewPager = rootView.findViewById(R.id.view_pager);
        viewPager.setAdapter(logoPagerAdapter);
        final TabLayout mTabLayout = rootView.findViewById(R.id.tablayout);
        mTabLayout.setupWithViewPager(viewPager);
        prevButton = rootView.findViewById(R.id.prev_button);
        nextButton = rootView.findViewById(R.id.next_button);
        enterButton = rootView.findViewById(R.id.enter);
        loginEditText = rootView.findViewById(R.id.Login);
        passwordEditText = rootView.findViewById(R.id.Password);
        loginEditTextSettings(loginEditText);
        passwordEditTextSettings(passwordEditText);
        viewPagerSettings(viewPager);
        btnSettings();
    }

    private void changeEnableEnter() {
        if (enableEnter[0] && enableEnter[1]) {
            enterButton.setEnabled(true);
        } else
            enterButton.setEnabled(false);
    }

    private void loginEditTextSettings(@NonNull final EditText editText) {
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
                enableEnter[0] = s.length() != 0;
                changeEnableEnter();
            }
        });
        editText.setOnKeyListener((v, keyCode, event) -> {
            passwordEditText.requestFocus();
            return true;
        });
    }

    private void passwordEditTextSettings(@NonNull final EditText editText) {
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
                enableEnter[1] = s.length() != 0;
                changeEnableEnter();
            }
        });
        editText.setOnKeyListener((v, keyCode, event) -> {
            boolean consumed = false;
            if (keyCode == KEYCODE_ENTER && !authorized) {
                if (enterButton.isEnabled()) {
                    authorized = true;
                    enterButton.callOnClick();
                }
                consumed = true;
            }
            return consumed;
        });
    }

    private void viewPagerSettings(@NonNull final ViewPager viewPager) {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                final int totalItems = logoPagerAdapter.getCount();
                controller.logoSelected(position);
                if (position == 0) {
                    prevButton.setVisibility(View.INVISIBLE);
                } else if (position == totalItems - 1) {
                    nextButton.setVisibility(View.INVISIBLE);
                } else {
                    prevButton.setVisibility(View.VISIBLE);
                    nextButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void btnSettings() {
        nextButton.setOnClickListener(v -> {
            final int current = viewPager.getCurrentItem();
            final int totalItems = logoPagerAdapter.getCount();
            if (current < totalItems - 1) {
                viewPager.setCurrentItem(current + 1, true);
            }
        });
        prevButton.setOnClickListener(v -> {
            final int current = viewPager.getCurrentItem();
            if (current != 0) {
                viewPager.setCurrentItem(current - 1, true);
            }
        });
        enterButton.setOnClickListener(v -> controller.enterBtnClick(loginEditText.getText().toString(), passwordEditText.getText().toString()));
    }


}
