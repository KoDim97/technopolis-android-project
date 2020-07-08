package com.example.technopolis.save;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.example.technopolis.App;

import java.util.List;

public class SaveCookieController {

    private String COOKIE = "COOKIE";
    private String COOKIE_CSRF_TOKEN = "CSRF_TOKEN";
    private String COOKIE_SESSION_ID = "SESSION_ID";
    private String COOKIE_DOMAIN = "DOMAIN";
    private final SharedPreferences cookiesPrefs;

    public SaveCookieController(@NonNull final App app) {
        cookiesPrefs = app.getSharedPreferences(COOKIE, Context.MODE_PRIVATE);
    }

    public void saveCookies(@NonNull final List<String> cookies) {
        final SharedPreferences.Editor editor = cookiesPrefs.edit();
        editor.putString(COOKIE_DOMAIN, cookies.get(0));
        editor.putString(COOKIE_CSRF_TOKEN, cookies.get(1));
        editor.putString(COOKIE_SESSION_ID, cookies.get(2));
        editor.apply();
    }

    public void setCookies() {
        android.webkit.CookieManager webkitCookies = android.webkit.CookieManager.getInstance();
        String domain = cookiesPrefs.getString(COOKIE_DOMAIN, "");
        String csrfToken = cookiesPrefs.getString(COOKIE_CSRF_TOKEN, "");
        String sessionId = cookiesPrefs.getString(COOKIE_SESSION_ID, "");
        webkitCookies.setCookie(domain, csrfToken);
        webkitCookies.setCookie(domain, sessionId);
    }

}
