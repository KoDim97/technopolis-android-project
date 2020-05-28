package com.example.technopolis.api;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.technopolis.R;
import com.example.technopolis.api.dto.AuthDto;
import com.example.technopolis.screens.common.nav.ScreenNavigator;
import com.example.technopolis.user.model.User;
import com.example.technopolis.util.ThreadPoster;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class ApiHelper {

    private Context context;
    private ThreadPoster mainThreadPoster;
    private Queue<Integer> messages = new LinkedBlockingQueue<>();

    public ApiHelper(Context context, ThreadPoster mainThreadPoster) {
        this.context = context;
        this.mainThreadPoster = mainThreadPoster;
    }

    public Integer getMessage() {
        return messages.poll();
    }

    public void setMessage(Integer message) {
        messages.offer(message);
    }

    public int size() {
        return messages.size();
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public boolean showMessageIfExist(MailApi api, ScreenNavigator screenNavigator, Runnable load) {
        Integer message = getMessage();
        if (message != null) {
            switch (message){
                case R.string.networkError:
                    if (!isOnline()) {
                        mainThreadPoster.post(() -> Toast.makeText(context, message, Toast.LENGTH_SHORT).show());
                    }
                    break;
                case R.string.reloadRequest:
                    clear();
                    reloadAuthToken(api);
                    load.run();
                    break;
                case R.string.authFailed:
                    mainThreadPoster.post(() -> screenNavigator.changeAuthorized(false));
                    break;
                default:
                    mainThreadPoster.post(() -> Toast.makeText(context, message, Toast.LENGTH_SHORT).show());
            }
            return true;
        }
        return false;
    }

    public static void reloadAuthToken(MailApi api) {
        User user = api.getUser();
        AuthDto authDto = api.requestAuthDto(user.getLogin(), user.getPassword());
        user.setAuth_token(authDto.getAuth_token());
    }

    public void clear() {
        messages.clear();
    }
}
