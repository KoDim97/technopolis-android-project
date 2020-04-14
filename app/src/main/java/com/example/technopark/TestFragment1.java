package com.example.technopark;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TestFragment1 extends Fragment {

    public TestFragment1() {
    }

    public static TestFragment1 newInstance() {
        return new TestFragment1();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.test_fragment1_fragment, container, false);
    }
}
