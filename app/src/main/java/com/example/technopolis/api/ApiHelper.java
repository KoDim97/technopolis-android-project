package com.example.technopolis.api;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.technopolis.BaseActivity;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class ApiHelper {

    private Queue<Integer> messages = new LinkedBlockingQueue<>();

    public Integer getMessage() {
        return messages.poll();
    }

    public void setMessage(Integer message) {
        messages.offer(message);
    }

    public int size() {
        return messages.size();
    }

    public boolean isOnline(BaseActivity activity) {
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting())
        {
            return true;
        }
        return false;
    }

    public void clear() {
        messages.clear();
    }
}
