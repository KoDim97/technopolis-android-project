package com.example.technopolis.save;

import android.content.Context;

import androidx.annotation.NonNull;

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

class SaveSchedulerController {
    private static final String fileName = "SchedulerRepoDisk";

    /**
     * Save scheduler's information  in "SchedulerRepoDisk" file
     * number of scheduler's items |
     * | size of scheduler's item's id in bytes | scheduler's item's id in bytes
     * | size of scheduler's item's subject's name in bytes | scheduler's subject's name in bytes
     * |size of scheduler's item's lesson's name in bytes | scheduler's item's lesson's name in bytes
     * |size of scheduler's item's lesson's type in bytes | scheduler's lesson's type in bytes
     * |size of scheduler's item's start time in bytes | scheduler's item's start time in bytes
     * |size of scheduler's item's end time in bytes | scheduler's item's end time in bytes
     * |size of scheduler's item's location in bytes | scheduler's item's location in bytes
     * |size of scheduler's item's date in bytes | scheduler's item's date in bytes
     * |size of scheduler's item's feedback's url in bytes | scheduler's item's feedback's url in bytes
     * |size of scheduler's item's isAttended flag in bytes | scheduler's item's isAttended flag in bytes
     * |size of scheduler's item's isCheckInOpen flag in bytes | scheduler's item's isCheckInOpen flag in bytes
     */
    static void serialize(@NonNull final SchedulerItemRepo schedulerItemRepo, @NonNull final App app) throws IOException {
        final FileOutputStream writer = app.getApplicationContext().openFileOutput(fileName, Context.MODE_PRIVATE);
        writer.write(schedulerItemRepo.findAll().size());
        for (final SchedulerItem item : schedulerItemRepo.findAll()) {
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

    /**
     * Read user's profile's information from "SchedulerRepoDisk" file
     *
     * @return true if read all scheduler's information else false
     */
    static boolean read(@NonNull final SchedulerItemRepo[] schedulerItemRepo, @NonNull final App app) throws IOException {
        FileInputStream reader;
        try {
            reader = app.getApplicationContext().openFileInput(fileName);
        } catch (FileNotFoundException e) {
            return false;
        }

        final int size = reader.read();
        final List<SchedulerItem> itemList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            int sizeRead = reader.read();
            if (sizeRead == -1)
                return false;
            byte[] buf = new byte[sizeRead];
            if (reader.read(buf) != sizeRead)
                return false;
            final long id = Long.parseLong(new String(buf));

            sizeRead = reader.read();
            if (sizeRead == -1)
                return false;
            buf = new byte[sizeRead];
            if (reader.read(buf) != sizeRead)
                return false;
            final String subjectName = new String(buf);

            sizeRead = reader.read();
            if (sizeRead == -1)
                return false;
            buf = new byte[sizeRead];
            if (reader.read(buf) != sizeRead)
                return false;
            final String lessonName = new String(buf);

            sizeRead = reader.read();
            if (sizeRead == -1)
                return false;
            buf = new byte[sizeRead];
            if (reader.read(buf) != sizeRead)
                return false;
            final String lessonType = new String(buf);

            sizeRead = reader.read();
            if (sizeRead == -1)
                return false;
            buf = new byte[sizeRead];
            if (reader.read(buf) != sizeRead)
                return false;
            final String startTime = new String(buf);

            sizeRead = reader.read();
            if (sizeRead == -1)
                return false;
            buf = new byte[sizeRead];
            if (reader.read(buf) != sizeRead)
                return false;
            final String endTime = new String(buf);

            sizeRead = reader.read();
            if (sizeRead == -1)
                return false;
            buf = new byte[sizeRead];
            if (reader.read(buf) != sizeRead)
                return false;
            final String location = new String(buf);

            sizeRead = reader.read();
            if (sizeRead == -1)
                return false;
            buf = new byte[sizeRead];
            if (reader.read(buf) != sizeRead)
                return false;
            final String date = new String(buf);

            sizeRead = reader.read();
            if (sizeRead == -1)
                return false;
            buf = new byte[sizeRead];
            if (reader.read(buf) != sizeRead)
                return false;
            final String feedbackUrl = new String(buf);

            sizeRead = reader.read();
            if (sizeRead == -1)
                return false;
            buf = new byte[sizeRead];
            if (reader.read(buf) != sizeRead)
                return false;
            final boolean isAttended = Boolean.parseBoolean(new String(buf));

            sizeRead = reader.read();
            if (sizeRead == -1)
                return false;
            buf = new byte[sizeRead];
            if (reader.read(buf) != sizeRead)
                return false;
            final boolean isCheckInOpen = Boolean.parseBoolean(new String(buf));

            itemList.add(SchedulerItem.newInstance(id, subjectName, lessonName, lessonType, startTime, endTime, location, date, isCheckInOpen, isAttended, feedbackUrl));
        }
        schedulerItemRepo[0] = new SchedulerItemRepoImpl();
        schedulerItemRepo[0].updateAll(itemList);
        reader.close();
        return true;
    }
}
