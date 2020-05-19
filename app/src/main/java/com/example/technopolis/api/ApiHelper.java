package com.example.technopolis.api;

import java.util.LinkedList;
import java.util.Queue;

public class ApiHelper {

    private Queue<String> messages = new LinkedList<>();

    public String getMessage() {
        return messages.poll();
    }

    public void setMessage(String message) {
        messages.offer(message);
    }
}
