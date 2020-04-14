package com.example.technopark;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TestFragment2 extends Fragment {

    public TestFragment2() {
    }

    public static TestFragment2 newInstance() {
        return new TestFragment2();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.test_fragment2_fragment, container, false);
    }
}
