package com.example.technopark.api;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
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
        return Arrays.asList(
                new NewsDto(
                        2,
                        "Иван Метелёв",
                        "Упражнения после лекции",
                        "Frontend-разработка 2019",
                        "21 октября 2019 г. 12:34",
                        "http://lpark.localhost/media/avatars/gtp/07/01/2121301dde1bec34b962291dad9093ef.jpg",
                        "2"
                ),
                new NewsDto(
                        2,
                        "Филипп Федчин",
                        "День открытых дверей Технополиса",
                        "Мероприятия",
                        "2 декабря 2019 г. 2:56",
                        "http://lpark.localhost/media/avatars/gtp/07/01/2121301dde1bec34b962291dad9093ef.jpg",
                        "0"
                )
        );
    }

    @Override
    public List<NewsDto> requestSubscribedNewsDto(Integer limit, Integer offset) {
        return Arrays.asList(
                new NewsDto(
                        2,
                        "Иван Метелёв",
                        "Упражнения после лекции",
                        "Frontend-разработка 2019",
                        "21 октября 2019 г. 12:34",
                        "http://lpark.localhost/media/avatars/gtp/07/01/2121301dde1bec34b962291dad9093ef.jpg",
                        "2"
                ),
                new NewsDto(
                        2,
                        "Филипп Федчин",
                        "День открытых дверей Технополиса",
                        "Мероприятия",
                        "2 декабря 2019 г. 2:56",
                        "http://lpark.localhost/media/avatars/gtp/07/01/2121301dde1bec34b962291dad9093ef.jpg",
                        "0"
                )
        );
    }

    @Override
    public List<SchedulerItemDto> requestSchedulerItems() {

        ArrayList<SchedulerItemDto> items = new ArrayList<>();
        final String url = "https://polis.mail.ru/api/mobile/v1/schedule/";

        Map<String, String> mHeaders = new HashMap<>();
        mHeaders.put("Authorization", "Token " + user.getAuth_token());

        RequestFuture<JSONArray> requestFuture = RequestFuture.newFuture();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,  new JSONArray(), requestFuture, requestFuture ) {
            @Override
            public Map<String, String> getHeaders() {
                return mHeaders;
            }
        };
        queue.add(jsonArrayRequest);


        try {
            JSONArray response = requestFuture.get(2, TimeUnit.SECONDS);
            int count = 0;
            while (count < response.length()) {
                JSONObject jsonObject = response.getJSONObject(count);
                SchedulerItemDto schedulerItemDto = new SchedulerItemDto(
                        jsonObject.getInt("id"),
                        jsonObject.getString("discipline"),
                        jsonObject.getString("title"),
                        jsonObject.getString("short_title"),
                        jsonObject.getString("super_short_title"),
                        jsonObject.getString("date"),
                        jsonObject.getString("start_time"),
                        jsonObject.getString("end_time"),
                        jsonObject.getString("location"),
                        jsonObject.getBoolean("checkin_opened"),
                        jsonObject.getBoolean("attended"),
                        jsonObject.getString("feedback_url")
                );
                items.add(schedulerItemDto);
                ++count;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (JSONException e) {
        e.printStackTrace();
        }
        return items;
    }

    @Override
    public SchedulerItemCheckInDto checkInSchedulerItem(long id) {
        final String url = "https://polis.mail.ru/api/mobile/v1/schedule/" + id + "/check";
        JSONObject json = new JSONObject();

        Map<String, String> mHeaders = new HashMap<>();
        mHeaders.put("Authorization", "Token " + user.getAuth_token());

        RequestFuture<JSONObject> requestFuture=RequestFuture.newFuture();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, json, requestFuture, requestFuture)  {
            @Override
            public Map<String, String> getHeaders() {
                return mHeaders;
            }
        };
        queue.add(request);

        SchedulerItemCheckInDto schedulerItemCheckInDto = null;
        try {
            JSONObject response = requestFuture.get(2, TimeUnit.SECONDS);

            schedulerItemCheckInDto = new SchedulerItemCheckInDto(
                    response.getInt("schedule_item"),
                    response.getString("feedback_url")
            );
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return schedulerItemCheckInDto;
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
