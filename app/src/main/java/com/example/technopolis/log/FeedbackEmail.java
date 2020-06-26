package com.example.technopolis.log;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import androidx.core.content.FileProvider;
import android.text.TextUtils;

import com.example.technopolis.BuildConfig;
import com.example.technopolis.R;

import java.io.File;
import java.util.ArrayList;

public class FeedbackEmail {
    private final Activity activity;
    private String email = "ilya.gusarov.2000@yandex.ru";
    private String subject = "";
    private String content = "";
    private ArrayList<File> cacheAttaches = new ArrayList<>();

    public FeedbackEmail(Activity activity) {
        this.activity = activity;
    }

    public FeedbackEmail setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public FeedbackEmail setEmail(String email) {
        this.email = email;
        return this;
    }

    public FeedbackEmail setContent(String content) {
        this.content = content;
        return this;
    }

    public FeedbackEmail cacheAttach(String name) {
        File file = new File(activity.getCacheDir(), name + ".log");
        if (file.length() > 0) {
            cacheAttaches.add(file);
        }

        return this;
    }

    public FeedbackEmail build() {
        if (subject.isEmpty()) {
            subject = activity.getString(R.string.app_name) + " Feedback";
        }

        if (content.isEmpty()) {
            content = buildContent();
        }

        return this;
    }

    private String buildContent() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 4; i++) builder.append("\n");

        builder.append("App version: " + BuildConfig.VERSION_NAME + "\n");
        builder.append("Debuggable: " + LogHelper.DEBUG + "\n");
        builder.append("Device: " + Build.MANUFACTURER + " " + Build.PRODUCT + " " + Build.MODEL + "\n");
        builder.append("Android: " + android.os.Build.VERSION.RELEASE + " (API " + Build.VERSION.SDK_INT + ")");

        return builder.toString();
    }

    public void send() {
        String action = cacheAttaches.size() > 0 ? Intent.ACTION_SEND_MULTIPLE : Intent.ACTION_SEND;

        Intent emailIntent = new Intent(action);
        emailIntent.setType("plain/text");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{email});
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, content);

        ArrayList<Uri> uris = new ArrayList<>();

        for (File file : cacheAttaches) {
            Uri contentUri = FileProvider.getUriForFile(activity, BuildConfig.APPLICATION_ID + ".fileprovider", file);
            uris.add(contentUri);
        }

        emailIntent.putParcelableArrayListExtra(android.content.Intent.EXTRA_STREAM, uris);
        activity.startActivity(emailIntent);
    }

}