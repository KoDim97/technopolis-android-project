package com.example.technopark.api;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.example.technopark.api.dto.AuthDto;
import com.example.technopark.api.dto.GroupDto;
import com.example.technopark.api.dto.NewsDto;
import com.example.technopark.api.dto.ProfileDto;
import com.example.technopark.api.dto.SchedulerItemCheckInDto;
import com.example.technopark.api.dto.SchedulerItemDto;
import com.example.technopark.user.model.User;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MailApiImpl implements MailApi {
    private RequestQueue queue;
    private User user;

    public MailApiImpl(RequestQueue queue, User user) {
        this.queue = queue;
        this.user = user;
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
                    (req_id + "***REMOVED***")
                            .getBytes(StandardCharsets.UTF_8));
            String token = bytesToHex(encodedHash);
            json.put("req_id", req_id);
            json.put("token", token);
            json.put("login", login);
            json.put("password", password);
        } catch (NoSuchAlgorithmException | JSONException e) {
            e.printStackTrace();
        }

        RequestFuture<JSONObject> requestFuture = RequestFuture.newFuture();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, json, requestFuture, requestFuture);
        queue.add(request);

        try {
            JSONObject response = requestFuture.get(2, TimeUnit.SECONDS);

            String username = response.getString("username");
            String auth_token = response.getString("auth_token");
            Integer user_id = response.getInt("user_id");
            authDto = new AuthDto(auth_token, user_id, username);
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

    public ProfileDto requestProfileDto(long id) {
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
    public List<NewsDto> requestMainNewsDto(Integer limit, Integer offset) {
        final List<NewsDto> newsDtoList = new ArrayList<>();
        final StringBuilder url = new StringBuilder("https://polis.mail.ru/api/mobile/v1/topics/main/?");
        url.append("limit=").append(limit).append("&offset=").append(offset);
        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(
                Request.Method.GET,
                url.toString(),
                null,
                future,
                future) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Token " + user.getAuth_token());
                return headers;
            }
        };
        queue.add(jsonArrayRequest);
        try {
            JSONObject response = future.get();
            JSONArray results = response.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject one_new = results.getJSONObject(i);
                long id = one_new.getLong("id");
                String fullname = one_new.getJSONObject("author").getString("fullname");
                String title = one_new.getString("title");
                String blog = one_new.getString("blog");
                String publish_date = one_new.getString("publish_date");
                String avatar_url = one_new.getJSONObject("author").getString("avatar_url");
                String comments_count = one_new.getString("comments_count");

                newsDtoList.add(new NewsDto(
                        id,
                        fullname,
                        title,
                        blog,
                        publish_date,
                        avatar_url,
                        comments_count
                ));
            }


        } catch (JSONException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return newsDtoList;
    }

    @Override
    public List<NewsDto> requestSubscribedNewsDto(Integer limit, Integer offset) {
        final List<NewsDto> newsDtoList = new ArrayList<>();
        final StringBuilder url = new StringBuilder("https://polis.mail.ru/api/mobile/v1/topics/subscribed/?");
        url.append("limit=").append(limit).append("&offset=").append(offset);
        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(
                Request.Method.GET,
                url.toString(),
                null,
                future,
                future) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Token "  + user.getAuth_token());
                return headers;
            }
        };
        queue.add(jsonArrayRequest);
        try {
            JSONObject response = future.get();
            JSONArray results = response.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject one_new = results.getJSONObject(i);
                long id = one_new.getLong("id");
                String fullname = one_new.getJSONObject("author").getString("fullname");
                String title = one_new.getString("title");
                String blog = one_new.getString("blog");
                String publish_date = one_new.getString("publish_date");
                String avatar_url = one_new.getJSONObject("author").getString("avatar_url");
                String comments_count = one_new.getString("comments_count");

                newsDtoList.add(new NewsDto(
                        id,
                        fullname,
                        title,
                        blog,
                        publish_date,
                        avatar_url,
                        comments_count
                ));
            }


        } catch (JSONException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return newsDtoList;
    }

    @Override
    public List<SchedulerItemDto> requestSchedulerItems() {
        return Arrays.asList(
                new SchedulerItemDto(
                        123L,
                        "Использование баз данных",
                        "Оптимизация запросов и индексирование",
                        "Лекция 6",
                        "Л 6",
                        "2020-04-08T00:00:00Z",
                        "2020-04-08T18:30:00Z",
                        "2020-04-08T21:30:00Z",
                        "онлайн",
                        false,
                        false,
                        null
                ),
                new SchedulerItemDto(
                        213L,
                        "Использование баз данных",
                        "Оптимизация запросов и индексирование",
                        "Лекция 6",
                        "Л 6",
                        "2020-04-08T00:00:00Z",
                        "2020-04-08T18:30:00Z",
                        "2020-04-08T21:30:00Z",
                        "онлайн",
                        false,
                        false,
                        null
                )
        );
    }

    @Override
    public SchedulerItemCheckInDto checkInSchedulerItem(long id) {
        return new SchedulerItemCheckInDto(123L, "someURL");
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
