package com.example.technopolis.log;

import android.util.Log;

import com.example.technopolis.App;
import com.example.technopolis.BuildConfig;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class LogHelper {
    public static final String TAG = "Logging";
    public static final boolean DEBUG = BuildConfig.DEBUG;
    public static String FILENAME;

    public static void i(String tag, String msg) {
        msg = msg.isEmpty() ? "" : msg;
        Log.i(tag, msg);
        LogHelper.appendLog(LogHelper.FILENAME, msg);
    }

    public static void i(Object caller, String tag, String msg) {
        i(tag, caller.getClass().getSimpleName() + "." + msg);
    }

    public static void i(Object caller, String msg) {
        i(caller, TAG, msg);
    }

    public static void i(String msg) {
        i(TAG, msg);
    }

    public static void e(String tag, String msg) {
        msg = msg.isEmpty() ? "" : msg;
        Log.e(tag, msg);
        LogHelper.appendLog(LogHelper.FILENAME, msg);
    }

    public static void e(Object caller, String tag, String msg) {
        e(tag, caller.getClass().getSimpleName() + "." + msg);
    }

    public static void e(Object caller, String msg) {
        e(caller, TAG, msg);
    }

    public static void e(String msg) {
        e(TAG, msg);
    }

    public static File getLogFile(String filename) {
        File file = new File(App.getApplication().getCacheDir(), filename + ".log");
        return file;
    }

    public static void createLog(String filename) {
        LogHelper.FILENAME = filename;
        File file = getLogFile(filename);
        if (!file.exists()) {
            try {
                file.createNewFile();

                appendLog(filename, "Created at " + new Date().toString());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void appendLog(String filename, String line) {
        File file = getLogFile(filename);
        if (!file.exists()) {
            createLog(filename);
        }

        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
            bufferedWriter.write(line);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
