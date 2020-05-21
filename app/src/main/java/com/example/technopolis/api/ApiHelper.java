package com.example.technopolis.api;

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
}
