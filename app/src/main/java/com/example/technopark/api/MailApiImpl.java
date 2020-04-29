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
        String url = "https://polis.mail.ru/api/mobile/v1/schedule/";

        Map<String, String> mHeaders = new HashMap<>();
        mHeaders.put("Authorization", "Token " + user.getAuth_token());

        RequestFuture<JSONArray> requestFuture = RequestFuture.newFuture();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,  new JSONArray(), requestFuture, requestFuture ) {
            @Override
            public Map<String, String> getHeaders() {
                return mHeaders;
            }
        } ;
        queue.add(jsonArrayRequest);


        try {
            JSONArray response = requestFuture.get(5, TimeUnit.SECONDS);
            int count = 0;
            while (count < response.length()) {
                try {
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
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return items;

       /* return Arrays.asList(
                new SchedulerItemDto(
                        0L,
                        "Использование баз данных",
                        "Оптимизация запросов и индексирование",
                        "Лекция 6",
                        "Л 6",
                        "2020-04-08T00:00:00Z",
                        "2020-04-08T18:30:00Z",
                        "2020-04-08T21:30:00Z",
                        "онлайн",
                        false,
                        true,
                        null
                ),
                new SchedulerItemDto(
                        1L,
                        "Использование баз данных",
                        "Оптимизация запросов и индексирование",
                        "Лекция 6",
                        "Л 6",
                        "2020-04-09T00:00:00Z",
                        "2020-04-08T18:30:00Z",
                        "2020-04-08T21:30:00Z",
                        "онлайн",
                        true,
                        false,
                        null
                ),
                new SchedulerItemDto(
                        1223L,
                        "Использование баз данных",
                        "Оптимизация запросов и индексирование",
                        "Лекция 6",
                        "Л 6",
                        "2020-04-10T00:00:00Z",
                        "2020-04-08T18:30:00Z",
                        "2020-04-08T21:30:00Z",
                        "онлайн",
                        false,
                        false,
                        "someURl"
                ),
                new SchedulerItemDto(
                        1243L,
                        "Использование баз данных",
                        "Оптимизация запросов и индексирование",
                        "Лекция 6",
                        "Л 6",
                        "2020-04-11T00:00:00Z",
                        "2020-04-08T18:30:00Z",
                        "2020-04-08T21:30:00Z",
                        "онлайн",
                        false,
                        false,
                        null
                ),
                new SchedulerItemDto(
                        1235L,
                        "Использование баз данных",
                        "Оптимизация запросов и индексирование",
                        "Лекция 6",
                        "Л 6",
                        "2020-04-12T00:00:00Z",
                        "2020-04-08T18:30:00Z",
                        "2020-04-08T21:30:00Z",
                        "онлайн",
                        false,
                        false,
                        null
                ),
                new SchedulerItemDto(
                        12323L,
                        "Использование баз данных",
                        "Оптимизация запросов и индексирование",
                        "Лекция 6",
                        "Л 6",
                        "2020-04-13T00:00:00Z",
                        "2020-04-08T18:30:00Z",
                        "2020-04-08T21:30:00Z",
                        "онлайн",
                        false,
                        false,
                        null
                ),
                new SchedulerItemDto(
                        1265463L,
                        "Использование баз данных",
                        "Оптимизация запросов и индексирование",
                        "Лекция 6",
                        "Л 6",
                        "2020-04-14T00:00:00Z",
                        "2020-04-08T18:30:00Z",
                        "2020-04-08T21:30:00Z",
                        "онлайн",
                        false,
                        false,
                        null
                ),
                new SchedulerItemDto(
                        18623L,
                        "Использование баз данных",
                        "Оптимизация запросов и индексирование",
                        "Лекция 6",
                        "Л 6",
                        "2020-04-15T00:00:00Z",
                        "2020-04-08T18:30:00Z",
                        "2020-04-08T21:30:00Z",
                        "онлайн",
                        false,
                        false,
                        null
                ),
                new SchedulerItemDto(
                        21883L,
                        "Использование баз данных",
                        "Оптимизация запросов и индексирование",
                        "Лекция 6",
                        "Л 6",
                        "2020-04-16T00:00:00Z",
                        "2020-04-08T18:30:00Z",
                        "2020-04-08T21:30:00Z",
                        "онлайн",
                        false,
                        false,
                        null
                )
        );*/
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
