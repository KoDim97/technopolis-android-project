package com.example.technopark.api;

import com.android.volley.AuthFailureError;
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
import com.example.technopark.api.dto.StudentDto;
import com.example.technopark.user.model.User;
import com.example.technopark.api.dto.SchedulerItemCheckInDto;
import com.example.technopark.api.dto.SchedulerItemDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
            JSONObject response = requestFuture.get(5, TimeUnit.SECONDS);

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
    public GroupDto requestGroupDto(long id) {
        final String url = "https://polis.mail.ru/api/mobile/v1/groups/" + String.valueOf(id);

        RequestFuture<JSONObject> requestFuture=RequestFuture.newFuture();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(), requestFuture, requestFuture){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization", "Token " + user.getAuth_token());
                return params;
            }
        };
        queue.add(request);

        try {
            JSONObject response = requestFuture.get(5, TimeUnit.SECONDS);
            String name = response.getString("name");
            JSONArray students = response.getJSONArray("students");
            List<StudentDto> list = new ArrayList<>();
            for (int i = 0; i < students.length(); ++i){
                JSONObject student = students.getJSONObject(i);
                long student_id = student.getLong("id");
                String username = student.getString("username");
                String fullname = student.getString("fullname");
                String avatar_url = student.getString("avatar_url");
                boolean online = student.getBoolean("online");
                double rating = student.getDouble("rating");
                list.add(new StudentDto(student_id, username, fullname, avatar_url, online, rating));
            }
            return new GroupDto(id, name, list);
        } catch (InterruptedException | TimeoutException e) {
            System.out.println("Time out");
        } catch (ExecutionException e) {
            System.out.println("Update token");
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
