package com.example.technopolis.save;

import android.content.Context;

import com.example.technopolis.App;
import com.example.technopolis.scheduler.model.SchedulerItem;
import com.example.technopolis.scheduler.repo.SchedulerItemRepo;
import com.example.technopolis.scheduler.repo.SchedulerItemRepoImpl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SaveSchedulerController {
    private static String fileName = "SchedulerRepoDisk";

    static void serialize(SchedulerItemRepo schedulerItemRepo, App app) throws IOException {
        FileOutputStream writer = app.getApplicationContext().openFileOutput(fileName, Context.MODE_PRIVATE);
        writer.write(schedulerItemRepo.findAll().size());
        for (SchedulerItem item : schedulerItemRepo.findAll()) {
            writer.write(String.valueOf(item.getId()).getBytes().length);
            writer.write(String.valueOf(item.getId()).getBytes());
            writer.write(item.getSubjectName().getBytes().length);
            writer.write(item.getSubjectName().getBytes());
            writer.write(item.getLessonName().getBytes().length);
            writer.write(item.getLessonName().getBytes());
            writer.write(item.getLessonType().getBytes().length);
            writer.write(item.getLessonType().getBytes());
            writer.write(item.getStartTime().getBytes().length);
            writer.write(item.getStartTime().getBytes());
            writer.write(item.getEndTime().getBytes().length);
            writer.write(item.getEndTime().getBytes());
            writer.write(item.getLocation().getBytes().length);
            writer.write(item.getLocation().getBytes());
            writer.write(item.getDate().getBytes().length);
            writer.write(item.getDate().getBytes());
            writer.write(item.getFeedbackUrl().getBytes().length);
            writer.write(item.getFeedbackUrl().getBytes());
            writer.write(String.valueOf(item.isAttended()).getBytes().length);
            writer.write(String.valueOf(item.isAttended()).getBytes());
            writer.write(String.valueOf(item.isCheckInOpen()).getBytes().length);
            writer.write(String.valueOf(item.isCheckInOpen()).getBytes());
        }
        writer.close();
    }

    static boolean read(SchedulerItemRepo[] schedulerItemRepo, App app) throws IOException {
        FileInputStream reader;
        try {
            reader = app.getApplicationContext().openFileInput(fileName);
        } catch (FileNotFoundException e) {
            return false;
        }

        int size = reader.read();
        List<SchedulerItem> itemList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            int sizeRead = reader.read();
            byte[] buf = new byte[sizeRead];
            if (reader.read(buf) != sizeRead)
                return false;
            long id = Long.parseLong(new String(buf));
            sizeRead = reader.read();
            buf = new byte[sizeRead];
            if (reader.read(buf) != sizeRead)
                return false;
            String subjectName = new String(buf);
            sizeRead = reader.read();
            buf = new byte[sizeRead];
            if (reader.read(buf) != sizeRead)
                return false;
            String lessonName = new String(buf);
            sizeRead = reader.read();
            buf = new byte[sizeRead];
            if (reader.read(buf) != sizeRead)
                return false;
            String lessonType = new String(buf);
            sizeRead = reader.read();
            buf = new byte[sizeRead];
            if (reader.read(buf) != sizeRead)
                return false;
            String startTime = new String(buf);
            sizeRead = reader.read();
            buf = new byte[sizeRead];
            if (reader.read(buf) != sizeRead)
                return false;
            String endTime = new String(buf);
            sizeRead = reader.read();
            buf = new byte[sizeRead];
            if (reader.read(buf) != sizeRead)
                return false;
            String location = new String(buf);
            sizeRead = reader.read();
            buf = new byte[sizeRead];
            if (reader.read(buf) != sizeRead)
                return false;
            String date = new String(buf);
            sizeRead = reader.read();
            buf = new byte[sizeRead];
            if (reader.read(buf) != sizeRead)
                return false;
            String feedbackUrl = new String(buf);
            sizeRead = reader.read();
            buf = new byte[sizeRead];
            if (reader.read(buf) != sizeRead)
                return false;
            boolean isAttended = Boolean.parseBoolean(new String(buf));
            sizeRead = reader.read();
            buf = new byte[sizeRead];
            if (reader.read(buf) != sizeRead)
                return false;
            boolean isCheckInOpen = Boolean.parseBoolean(new String(buf));
            itemList.add(SchedulerItem.newInstance(id, subjectName, lessonName, lessonType, startTime, endTime, location, date, isCheckInOpen, isAttended, feedbackUrl));
        }
        schedulerItemRepo[0] = new SchedulerItemRepoImpl();
        schedulerItemRepo[0].updateAll(itemList);
        reader.close();
        return true;
    }
}
