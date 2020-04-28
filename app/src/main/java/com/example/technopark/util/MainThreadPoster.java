package com.example.technopark.util;

import android.os.Handler;
import android.os.Looper;

public class MainThreadPoster implements ThreadPoster {

    Handler mainHandler = new Handler(Looper.getMainLooper());

    @Override public void post(Runnable runnable) {
        mainHandler.post(runnable);
    }
}
