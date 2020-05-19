package com.example.technopolis.api;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class ApiHelper {

    private Queue<String> messages = new LinkedBlockingQueue<>();

    public String getMessage() {
        return messages.poll();
    }

    public void setMessage(String message) {
        messages.offer(message);
    }
}
