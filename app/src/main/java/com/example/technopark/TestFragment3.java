package com.example.technopark;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TestFragment3 extends Fragment {

    public TestFragment3() {
    }

    public static TestFragment3 newInstance() {
        return new TestFragment3();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.test_fragment3_fragment, container, false);
    }
}
