package com.example.technopolis.save;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.technopolis.App;
import com.example.technopolis.images.repo.ImagesRepo;
import com.example.technopolis.profile.model.UserAccount;
import com.example.technopolis.profile.model.UserContact;
import com.example.technopolis.profile.model.UserGroup;
import com.example.technopolis.profile.model.UserProfile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class SaveProfileController {
    private final static String fileName = "ProfileRepoDisk";

    static void serialize(@NonNull final UserProfile profile, @NonNull final App app) throws IOException {
        final FileOutputStream writer = app.getApplicationContext().openFileOutput(fileName, Context.MODE_PRIVATE);
        writer.write(String.valueOf(profile.getId()).getBytes().length);
        writer.write(String.valueOf(profile.getId()).getBytes());

        writer.write(profile.getUserName().getBytes().length);
        writer.write(profile.getUserName().getBytes());

        writer.write(String.valueOf(profile.getProjectId()).getBytes().length);
        writer.write(String.valueOf(profile.getProjectId()).getBytes());

        writer.write(profile.getProjectName().getBytes().length);
        writer.write(profile.getProjectName().getBytes());

        writer.write(profile.getFullName().getBytes().length);
        writer.write(profile.getFullName().getBytes());

        writer.write(profile.getGender().getBytes().length);
        writer.write(profile.getGender().getBytes());

        writer.write(profile.getAvatarUrl().getBytes().length);
        writer.write(profile.getAvatarUrl().getBytes());

        writer.write(profile.getMainGroup().getBytes().length);
        writer.write(profile.getMainGroup().getBytes());

        writer.write(profile.getBirthDate().getBytes().length);
        writer.write(profile.getBirthDate().getBytes());

        writer.write(profile.getAbout().getBytes().length);
        writer.write(profile.getAbout().getBytes());

        writer.write(profile.getJoinDate().getBytes().length);
        writer.write(profile.getJoinDate().getBytes());

        writer.write(profile.getLastSeen().getBytes().length);
        writer.write(profile.getLastSeen().getBytes());

        writer.write(profile.getContacts().size());
        for (final UserContact contact : profile.getContacts()) {
            writer.write(contact.getName().getBytes().length);
            writer.write(contact.getName().getBytes());
            writer.write(contact.getValue().getBytes().length);
            writer.write(contact.getValue().getBytes());
        }

        writer.write(profile.getGroups().size());
        for (final UserGroup group : profile.getGroups()) {
            writer.write(String.valueOf(group.getId()).getBytes().length);
            writer.write(String.valueOf(group.getId()).getBytes());
            writer.write(group.getName().getBytes().length);
            writer.write(group.getName().getBytes());
        }

        writer.write(profile.getAccounts().size());
        for (final UserAccount account : profile.getAccounts()) {
            writer.write(account.getName().getBytes().length);
            writer.write(account.getName().getBytes());
            writer.write(account.getValue().getBytes().length);
            writer.write(account.getValue().getBytes());
        }

        writer.close();
    }

    static boolean read(@NonNull final UserProfile[] userProfile, @NonNull final ImagesRepo imagesRepo, @NonNull final App app) throws IOException {
        FileInputStream reader;
        try {
            reader = app.getApplicationContext().openFileInput(fileName);
        } catch (FileNotFoundException e) {
            return false;
        }

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
        final String userName = new String(buf);

        sizeRead = reader.read();
        if (sizeRead == -1)
            return false;
        buf = new byte[sizeRead];
        if (reader.read(buf) != sizeRead)
            return false;
        final long project_id = Long.parseLong(new String(buf));

        sizeRead = reader.read();
        if (sizeRead == -1)
            return false;
        buf = new byte[sizeRead];
        if (reader.read(buf) != sizeRead)
            return false;
        final String projectName = new String(buf);

        sizeRead = reader.read();
        if (sizeRead == -1)
            return false;
        buf = new byte[sizeRead];
        if (reader.read(buf) != sizeRead)
            return false;
        final String fullName = new String(buf);

        sizeRead = reader.read();
        if (sizeRead == -1)
            return false;
        buf = new byte[sizeRead];
        if (reader.read(buf) != sizeRead)
            return false;
        final String gender = new String(buf);

        sizeRead = reader.read();
        if (sizeRead == -1)
            return false;
        buf = new byte[sizeRead];
        if (reader.read(buf) != sizeRead)
            return false;
        final String avatarUrl = new String(buf);

        sizeRead = reader.read();
        if (sizeRead == -1)
            return false;
        buf = new byte[sizeRead];
        if (reader.read(buf) != sizeRead)
            return false;
        final String mainGroup = new String(buf);

        sizeRead = reader.read();
        if (sizeRead == -1)
            return false;
        buf = new byte[sizeRead];
        if (reader.read(buf) != sizeRead)
            return false;
        final String birthDate = new String(buf);

        sizeRead = reader.read();
        if (sizeRead == -1)
            return false;
        buf = new byte[sizeRead];
        if (reader.read(buf) != sizeRead)
            return false;
        final String about = new String(buf);

        sizeRead = reader.read();
        if (sizeRead == -1)
            return false;
        buf = new byte[sizeRead];
        if (reader.read(buf) != sizeRead)
            return false;
        final String joinDate = new String(buf);

        sizeRead = reader.read();
        if (sizeRead == -1)
            return false;
        buf = new byte[sizeRead];
        if (reader.read(buf) != sizeRead)
            return false;
        final String lastSeen = new String(buf);

        int size = reader.read();
        final List<UserContact> contacts = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            sizeRead = reader.read();
            if (sizeRead == -1)
                return false;
            buf = new byte[sizeRead];
            if (reader.read(buf) != sizeRead)
                return false;
            final String nameContact = new String(buf);

            sizeRead = reader.read();
            if (sizeRead == -1)
                return false;
            buf = new byte[sizeRead];
            if (reader.read(buf) != sizeRead)
                return false;
            final String valueContact = new String(buf);
            contacts.add(new UserContact(nameContact, valueContact));
        }

        size = reader.read();
        final List<UserGroup> groups = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            sizeRead = reader.read();
            if (sizeRead == -1)
                return false;
            buf = new byte[sizeRead];
            if (reader.read(buf) != sizeRead)
                return false;
            final long idGroup = Long.parseLong(new String(buf));

            sizeRead = reader.read();
            if (sizeRead == -1)
                return false;
            buf = new byte[sizeRead];
            if (reader.read(buf) != sizeRead)
                return false;
            final String nameGroup = new String(buf);
            groups.add(new UserGroup(idGroup, nameGroup));
        }

        size = reader.read();
        final List<UserAccount> accounts = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            sizeRead = reader.read();
            if (sizeRead == -1)
                return false;
            buf = new byte[sizeRead];
            if (reader.read(buf) != sizeRead)
                return false;
            final String nameAccount = new String(buf);

            sizeRead = reader.read();
            if (sizeRead == -1)
                return false;
            buf = new byte[sizeRead];
            if (reader.read(buf) != sizeRead)
                return false;
            final String valueAccount = new String(buf);
            accounts.add(new UserAccount(nameAccount, valueAccount));
        }

        userProfile[0] = new UserProfile(id, userName, project_id, projectName, fullName, gender, imagesRepo.findById(avatarUrl), avatarUrl, mainGroup, birthDate, about, joinDate, lastSeen, contacts, groups, accounts);

        reader.close();
        return true;
    }
}
