package com.example.technopolis.save;

import android.content.Context;

import com.example.technopolis.App;
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

public class SaveProfileController {
    private static String fileName = "ProfileRepoDisk";

    static void serialize(UserProfile profile, App app) throws IOException {
        if (profile == null)
            throw new IOException("profile==null");
        FileOutputStream writer = app.getApplicationContext().openFileOutput(fileName, Context.MODE_PRIVATE);
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
        for (UserContact contact : profile.getContacts()) {
            writer.write(contact.getName().getBytes().length);
            writer.write(contact.getName().getBytes());
            writer.write(contact.getValue().getBytes().length);
            writer.write(contact.getValue().getBytes());
        }

        writer.write(profile.getGroups().size());
        for (UserGroup group : profile.getGroups()) {
            writer.write(String.valueOf(group.getId()).getBytes().length);
            writer.write(String.valueOf(group.getId()).getBytes());
            writer.write(group.getName().getBytes().length);
            writer.write(group.getName().getBytes());
        }

        writer.write(profile.getAccounts().size());
        for (UserAccount account : profile.getAccounts()) {
            writer.write(account.getName().getBytes().length);
            writer.write(account.getName().getBytes());
            writer.write(account.getValue().getBytes().length);
            writer.write(account.getValue().getBytes());
        }

        writer.close();
    }

    static boolean read(UserProfile[] userProfile, App app) throws IOException {
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
        long id = Long.parseLong(new String(buf));

        sizeRead = reader.read();
        buf = new byte[sizeRead];
        if (reader.read(buf) != sizeRead)
            return false;
        String userName = new String(buf);

        sizeRead = reader.read();
        buf = new byte[sizeRead];
        if (reader.read(buf) != sizeRead)
            return false;
        long project_id = Long.parseLong(new String(buf));

        sizeRead = reader.read();
        buf = new byte[sizeRead];
        if (reader.read(buf) != sizeRead)
            return false;
        String projectName = new String(buf);

        sizeRead = reader.read();
        buf = new byte[sizeRead];
        if (reader.read(buf) != sizeRead)
            return false;
        String fullName = new String(buf);

        sizeRead = reader.read();
        buf = new byte[sizeRead];
        if (reader.read(buf) != sizeRead)
            return false;
        String gender = new String(buf);

        sizeRead = reader.read();
        buf = new byte[sizeRead];
        if (reader.read(buf) != sizeRead)
            return false;
        String avatarUrl = new String(buf);

        sizeRead = reader.read();
        buf = new byte[sizeRead];
        if (reader.read(buf) != sizeRead)
            return false;
        String mainGroup = new String(buf);

        sizeRead = reader.read();
        buf = new byte[sizeRead];
        if (reader.read(buf) != sizeRead)
            return false;
        String birthDate = new String(buf);

        sizeRead = reader.read();
        buf = new byte[sizeRead];
        if (reader.read(buf) != sizeRead)
            return false;
        String about = new String(buf);

        sizeRead = reader.read();
        buf = new byte[sizeRead];
        if (reader.read(buf) != sizeRead)
            return false;
        String joinDate = new String(buf);

        sizeRead = reader.read();
        buf = new byte[sizeRead];
        if (reader.read(buf) != sizeRead)
            return false;
        String lastSeen = new String(buf);

        int size = reader.read();
        List<UserContact> contacts = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            sizeRead = reader.read();
            buf = new byte[sizeRead];
            if (reader.read(buf) != sizeRead)
                return false;
            String nameContact = new String(buf);

            sizeRead = reader.read();
            buf = new byte[sizeRead];
            if (reader.read(buf) != sizeRead)
                return false;
            String valueContact = new String(buf);
            contacts.add(new UserContact(nameContact, valueContact));
        }

        size = reader.read();
        List<UserGroup> groups = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            sizeRead = reader.read();
            buf = new byte[sizeRead];
            if (reader.read(buf) != sizeRead)
                return false;
            long idGroup = Long.parseLong(new String(buf));

            sizeRead = reader.read();
            buf = new byte[sizeRead];
            if (reader.read(buf) != sizeRead)
                return false;
            String nameGroup = new String(buf);
            groups.add(new UserGroup(idGroup, nameGroup));
        }

        size = reader.read();
        List<UserAccount> accounts = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            sizeRead = reader.read();
            buf = new byte[sizeRead];
            if (reader.read(buf) != sizeRead)
                return false;
            String nameAccount = new String(buf);

            sizeRead = reader.read();
            buf = new byte[sizeRead];
            if (reader.read(buf) != sizeRead)
                return false;
            String valueAccount = new String(buf);
            accounts.add(new UserAccount(nameAccount, valueAccount));
        }

        userProfile[0] = new UserProfile(id, userName, project_id, projectName, fullName, gender, avatarUrl, mainGroup, birthDate, about, joinDate, lastSeen, contacts, groups, accounts);

        reader.close();
        return true;
    }
}
