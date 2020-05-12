package com.example.technopolis.screens.authorization;


import android.widget.Toast;

import com.example.technopolis.App;
import com.example.technopolis.BaseActivity;
import com.example.technopolis.R;
import com.example.technopolis.screens.common.nav.ScreenNavigator;
import com.example.technopolis.user.service.AuthService;

public class AuthorizationViewControllerImpl implements AuthorizationViewController {
    private final App app;
    private final AuthService authService;
    private final ScreenNavigator screenNavigator;
    private final BaseActivity activity;

    public AuthorizationViewControllerImpl(BaseActivity activity) {
        this.activity = activity;
        app = (App) activity.getApplication();
        assert app != null;
        authService = app.provideAuthService();
        screenNavigator = activity.getScreenNavigator();
    }

    @Override
    public void logoSelected(int pos) {
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
    public void enterBtnClick(String login, String password) {
        //AtomicBoolean invalid = new AtomicBoolean(false);
        Thread thread = new Thread(() -> {
            if (authService.CheckAuth(login, password)) {
                activity.runOnUiThread(()->screenNavigator.changeAuthorized(true));
            } else {
                activity.runOnUiThread(() -> Toast.makeText(activity, R.string.authFailed, Toast.LENGTH_SHORT).show());
                screenNavigator.changeAuthorized(false);
                //invalid.set(true);
            }
        });
        thread.start();
//            try {
//                thread.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            if (invalid.compareAndSet(true, false)){
//                Toast.makeText(activity, R.string.authFailed, Toast.LENGTH_SHORT).show();
//            }
    }



}
