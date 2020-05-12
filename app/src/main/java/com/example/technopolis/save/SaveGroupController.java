package com.example.technopolis.save;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.technopolis.App;
import com.example.technopolis.group.model.GroupItem;
import com.example.technopolis.group.model.Student;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class SaveGroupController {
    private final static String fileName = "GroupRepoDisk";

    static void serialize(@NonNull GroupItem groupItem, @NonNull App app) throws IOException {
        final FileOutputStream writer = app.getApplicationContext().openFileOutput(fileName, Context.MODE_PRIVATE);
        writer.write(String.valueOf(groupItem.getId()).getBytes().length);
        writer.write(String.valueOf(groupItem.getId()).getBytes());

        writer.write(groupItem.getName().getBytes().length);
        writer.write(groupItem.getName().getBytes());

        writer.write(groupItem.getStudents().size());
        for (final Student student : groupItem.getStudents()) {
            writer.write(String.valueOf(student.getId()).getBytes().length);
            writer.write(String.valueOf(student.getId()).getBytes());

            writer.write(student.getUsername().getBytes().length);
            writer.write(student.getUsername().getBytes());

            writer.write(student.getFullname().getBytes().length);
            writer.write(student.getFullname().getBytes());

            writer.write(student.getAvatarUrl().getBytes().length);
            writer.write(student.getAvatarUrl().getBytes());
        }
        writer.close();
    }

    static boolean read(@NonNull GroupItem[] groupItem, @NonNull App app) throws IOException {
        FileInputStream reader;
        try {
            reader = app.getApplicationContext().openFileInput(fileName);
        } catch (FileNotFoundException e) {
            return false;
        }

        int sizeRead = reader.read();
        byte[] buf = new byte[sizeRead];
        if (reader.read(buf) != sizeRead)
            return false;
        final long id = Long.parseLong(new String(buf));

        sizeRead = reader.read();
        buf = new byte[sizeRead];
        if (reader.read(buf) != sizeRead)
            return false;
        final String name = new String(buf);

        int size = reader.read();
        final List<Student> students = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            sizeRead = reader.read();
            buf = new byte[sizeRead];
            if (reader.read(buf) != sizeRead)
                return false;
            final long id_student = Long.parseLong(new String(buf));

            sizeRead = reader.read();
            buf = new byte[sizeRead];
            if (reader.read(buf) != sizeRead)
                return false;
            final String studentName = new String(buf);

            sizeRead = reader.read();
            buf = new byte[sizeRead];
            if (reader.read(buf) != sizeRead)
                return false;
            final String studentFullName = new String(buf);

            sizeRead = reader.read();
            buf = new byte[sizeRead];
            if (reader.read(buf) != sizeRead)
                return false;
            final String studentAvatarUrl = new String(buf);
            students.add(new Student(id_student, studentName, studentFullName, studentAvatarUrl, false));
        }
        groupItem[0] = new GroupItem(id, name, students);
        reader.close();
        return true;
    }
}
