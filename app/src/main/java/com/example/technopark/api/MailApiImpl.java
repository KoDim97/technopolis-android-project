package com.example.technopark.api;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import com.example.technopark.api.dto.AuthDto;
import com.example.technopark.api.dto.GroupDto;
import com.example.technopark.api.dto.NewsDto;
import com.example.technopark.api.dto.ProfileDto;
import com.example.technopark.api.dto.ScheduleDto;
import com.example.technopark.user.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MailApiImpl implements MailApi {
    private RequestQueue queue;
    public MailApiImpl(RequestQueue queue) {
        this.queue = queue;
    }

    @Override
    public AuthDto requestAuthDto(String login, String password) {
        AuthDto authDto;
        final String url = "https://polis.mail.ru/api/mobile/v1/auth/";
        JSONObject json = new JSONObject();

        MessageDigest digest = null;
        try {
            String req_id = UUID.randomUUID().toString().replace("-", "");
            digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(
                    (req_id + "b3d0341e9c1b5b2b73fc84dc59de6ac9f81e2710154c780302915b8e9082b5ef")
                            .getBytes(StandardCharsets.UTF_8));
            String token = bytesToHex(encodedHash);
            json.put("req_id", req_id);
            json.put("token", token);
            json.put("login", login);
            json.put("password", password);
        } catch (NoSuchAlgorithmException | JSONException e) {
            e.printStackTrace();
        }

        RequestFuture<JSONObject> requestFuture=RequestFuture.newFuture();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, json, requestFuture, requestFuture);
        queue.add(request);

        try {
            JSONObject response = requestFuture.get(2, TimeUnit.SECONDS);

            String username = response.getString("username");
            String auth_token = response.getString("auth_token");
            Integer user_id = response.getInt("user_id");
            authDto = new AuthDto(auth_token,user_id,username);
            return authDto;
        } catch (InterruptedException | TimeoutException e) {
            System.out.println("Time out");
        } catch (ExecutionException e) {
            System.out.println("Invalid login or password");
        } catch (JSONException e) {
            System.out.println("Json creating failed");
        }
        return null;
    }

    @Override
    public ProfileDto requestProfileDto(String username) {
        return null;
    }

    @Override
    public ProfileDto requestMyProfileDto() {
        return null;
    }

    @Override
    public GroupDto requestGroupDto(Integer id) {
        return null;
    }

    @Override
    public NewsDto requestMainNewsDto(Integer limit, Integer offset) {
        return null;
    }

    @Override
    public NewsDto requestSubscribedNewsDto(Integer limit, Integer offset) {
        return null;
    }

    @Override
    public ScheduleDto requestScheduleDto() {
        return null;
    }

    @Override
    public void scheduleCheckIn(Integer index) {

    }

    private String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
