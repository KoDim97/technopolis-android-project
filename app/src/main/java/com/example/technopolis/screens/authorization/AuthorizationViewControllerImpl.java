package com.example.technopolis.screens.authorization;


import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.technopolis.App;
import com.example.technopolis.BaseActivity;
import com.example.technopolis.api.ApiHelper;
import com.example.technopolis.screens.common.nav.ScreenNavigator;
import com.example.technopolis.user.service.AuthService;

import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.List;

public class AuthorizationViewControllerImpl implements AuthorizationViewController {
    private final App app;
    private final AuthService authService;
    private final ScreenNavigator screenNavigator;
    private final BaseActivity activity;
    private final ApiHelper apiHelper;

    AuthorizationViewControllerImpl(@NonNull final BaseActivity activity) {
        this.activity = activity;
        app = (App) activity.getApplication();
        assert app != null;
        authService = app.provideAuthService();
        apiHelper = app.provideApiHelper();
        screenNavigator = activity.getScreenNavigator();
    }

    @Override
    public void logoSelected(final int pos) {
        switch (pos) {
            case 0:
                app.provideMailApi().setProjectUrl(EducationProject.polis);
                return;
            case 1:
                app.provideMailApi().setProjectUrl(EducationProject.park);
                return;
            case 2:
                app.provideMailApi().setProjectUrl(EducationProject.technoatom);
                return;
            case 3:
                app.provideMailApi().setProjectUrl(EducationProject.sphere);
                return;
            case 4:
                app.provideMailApi().setProjectUrl(EducationProject.track);
                return;
            case 5:
                app.provideMailApi().setProjectUrl(EducationProject.pgu);
                return;
            case 6:
                app.provideMailApi().setProjectUrl(EducationProject.vgu);
                return;
            default:
                app.provideMailApi().setProjectUrl(EducationProject.polis);
        }
    }

    @Override
    public void enterBtnClick(@NonNull final String login, @NonNull final String password) {
        if (!app.isAuthorized()) {
            final Thread thread = new Thread(() -> {
                if (authService.CheckAuth(login, password)) {
                    apiHelper.clear();

                    activity.runOnUiThread(() ->  {
                        screenNavigator.changeAuthorized(true);
                        boolean isDomainSaved = false;
                        List<String> cookies = new ArrayList<>();

                        android.webkit.CookieManager webkitCookies = android.webkit.CookieManager.getInstance();
                        for (HttpCookie cookie : app.provideCookieManager().getCookieStore().getCookies()) {
                            if (!isDomainSaved) {
                                cookies.add(cookie.getDomain());
                                isDomainSaved = true;
                            }
                            String next = cookie.getName() + "=" + cookie.getValue();
                            cookies.add(next);
                            webkitCookies.setCookie(cookie.getDomain(), next);
                        }

                        app.provideSaveCookieController().saveCookies(cookies);
                    });
                } else {
                    Integer message = apiHelper.getMessage();
                    if (message != null) {
                        activity.runOnUiThread(() -> {
                            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                        });
                    }
                    screenNavigator.changeAuthorized(false);
                }
            });
            thread.start();
        }
    }
}
