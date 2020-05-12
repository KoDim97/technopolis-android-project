package com.example.technopolis.save;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.technopolis.App;
import com.example.technopolis.user.model.User;

class SaveAuthorizationController {
    private static final String APP_PREFERENCES = "settings";
    private static final String APP_PREFERENCES_AUTHORIZED = "authorized";
    private static final String APP_PREFERENCES_LOGIN = "login";
    private static final String APP_PREFERENCES_PASSWORD = "password";
    private static final String APP_PREFERENCES_AUTHORIZATION_TOKEN = "token";
    private static final String APP_PREFERENCES_USER_NAME = "name";
    private static final String APP_PREFERENCES_USER_ID = "id";
    private SharedPreferences mSettings;

    SaveAuthorizationController(App app) {
        mSettings = app.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    }


    void saveAuthorizationInfo(User user) {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putBoolean(APP_PREFERENCES_AUTHORIZED, true);
        editor.putString(APP_PREFERENCES_LOGIN, user.getLogin());
        editor.putString(APP_PREFERENCES_PASSWORD, user.getPassword());
        editor.putString(APP_PREFERENCES_AUTHORIZATION_TOKEN, user.getAuth_token());
        editor.putString(APP_PREFERENCES_USER_NAME, user.getUsername());
        editor.putInt(APP_PREFERENCES_USER_ID, user.getUser_id());
        editor.apply();
    }

    void exit() {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putBoolean(APP_PREFERENCES_AUTHORIZED, false);
        editor.apply();
    }

    boolean getUser(User[] user) {
        if (!mSettings.getBoolean(APP_PREFERENCES_AUTHORIZED, false)) {
            return false;
        }
        if (!mSettings.contains(APP_PREFERENCES_LOGIN)) {
            return false;
        }
        if (!mSettings.contains(APP_PREFERENCES_PASSWORD)) {
            return false;
        }
        if (!mSettings.contains(APP_PREFERENCES_AUTHORIZATION_TOKEN)) {
            return false;
        }
        if (!mSettings.contains(APP_PREFERENCES_USER_ID)) {
            return false;
        }
        if (!mSettings.contains(APP_PREFERENCES_USER_NAME)) {
            return false;
        }
        user[0] = new User();
        user[0].setLogin(mSettings.getString(APP_PREFERENCES_LOGIN, ""));
        user[0].setPassword(mSettings.getString(APP_PREFERENCES_PASSWORD, ""));
        user[0].setAuth_token(mSettings.getString(APP_PREFERENCES_AUTHORIZATION_TOKEN, ""));
        user[0].setUsername(mSettings.getString(APP_PREFERENCES_USER_NAME, ""));
        user[0].setUser_id(mSettings.getInt(APP_PREFERENCES_USER_ID, 0));
        return true;

    }

    boolean getAuthorized() {
        if (mSettings.contains(APP_PREFERENCES_AUTHORIZED)) {
            return mSettings.getBoolean(APP_PREFERENCES_AUTHORIZED, false);
        }
        return false;
    }
}
