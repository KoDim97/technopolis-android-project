package com.example.technopolis.api;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.example.technopolis.R;
import com.example.technopolis.api.dto.AuthDto;
import com.example.technopolis.api.dto.GroupDto;
import com.example.technopolis.api.dto.NewsDto;
import com.example.technopolis.api.dto.ProfileDto;
import com.example.technopolis.api.dto.SchedulerItemCheckInDto;
import com.example.technopolis.api.dto.SchedulerItemDto;
import com.example.technopolis.api.dto.StudentDto;
import com.example.technopolis.profile.model.UserAccount;
import com.example.technopolis.profile.model.UserContact;
import com.example.technopolis.profile.model.UserGroup;
import com.example.technopolis.user.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MailApiImpl implements MailApi {
    private static final int NETWORK_ERROR_MESSAGE = R.string.networkError;
    private static final int INVALID_LOGIN_OR_PASSWORD_ERROR_MESSAGE = R.string.authFailed;
    private static final int RELOAD_REQUEST = R.string.reloadRequest;

    private RequestQueue queue;
    private User user;
    private ApiHelper apiHelper;
    private String projectUrl;
    private static final String TAG = "TAG";

    public MailApiImpl(RequestQueue queue, User user, ApiHelper apiHelper) {
        this.queue = queue;
        this.user = user;
        this.apiHelper = apiHelper;
        //default
        projectUrl = "https://polis.mail.ru";
    }

    @Override
    public void setProjectUrl(String string) {
        projectUrl = string;
    }


    private Map<String, String> getAuthHeader() {
        Map<String, String> params = new HashMap<>();
        params.put("Authorization", "Token " + user.getAuth_token());
        return params;
    }

    @Override
    public AuthDto requestAuthDto(String login, String password) {
        AuthDto authDto;
        final String url = projectUrl + "/api/mobile/v1/auth/";
        JSONObject json = new JSONObject();

        MessageDigest digest = null;
        try {
            String req_id = UUID.randomUUID().toString().replace("-", "");
            digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(
                    (req_id + Salt.salt)
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
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, json, requestFuture, error -> {
            // TODO: Handle error
            if (error.networkResponse == null) {
                apiHelper.setMessage(NETWORK_ERROR_MESSAGE);
            } else {
                apiHelper.setMessage(INVALID_LOGIN_OR_PASSWORD_ERROR_MESSAGE);
            }
        });

        queue.cancelAll(TAG);
        queue.add(request);

        try {
            JSONObject response = requestFuture.get(1, TimeUnit.SECONDS);

            String username = response.getString("username");
            String auth_token = response.getString("auth_token");
            Integer user_id = response.getInt("user_id");
            authDto = new AuthDto(auth_token, user_id, username);
            return authDto;
        } catch (TimeoutException e) {
            int a = 5;
            //Time out
            System.err.println("timeout");
        } catch (JSONException e) {
            int a = 5;
            System.err.println("jsonException");
            //Json creating failed
        } catch (ExecutionException | InterruptedException e) {
            int a = 5;
            System.err.println("another");
            //unknown error
        }
        return null;
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public ProfileDto requestProfileDto(String username) {
        ProfileDto profileDto;

        final String url = projectUrl + "/api/mobile/v1/profile/" + username;

        RequestFuture<JSONObject> requestFuture = RequestFuture.newFuture();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(), requestFuture, requestFuture) {
            @Override
            public Map<String, String> getHeaders() {
                return getAuthHeader();
            }
        };
        request.setTag(TAG);
        queue.add(request);

        try {
            JSONObject response = requestFuture.get(2, TimeUnit.SECONDS);
            JSONObject activity = response.getJSONObject("activity");

//            Convert JSONArray contacts to List<UserContact>
//            from response
            JSONArray jsonContacts = response.getJSONArray("contacts");
            List<UserContact> contacts = new ArrayList<>();
            int lenContacts = jsonContacts.length();
            for (int i = 0; i < lenContacts; i++) {
                JSONObject contact = new JSONObject(jsonContacts.get(i).toString());
                UserContact userContact = new UserContact(
                        contact.getString("name"),
                        contact.getString("value")
                );

                contacts.add(userContact);
            }

//            Convert JSONArray groups to List<Group> from response
            List<UserGroup> groups = new ArrayList<>();
            JSONArray jsonGroups = response.getJSONArray("subgroups");
            int lenGroups = jsonGroups.length();
            for (int i = 0; i < lenGroups; i++) {
                JSONObject contact = new JSONObject(jsonGroups.get(i).toString());
                UserGroup userGroup = new UserGroup(
                        contact.getLong("id"),
                        contact.getString("name")
                );

                groups.add(userGroup);
            }

//            Convert JSONArray accounts to List<UserAccount> from response
            List<UserAccount> accounts = new ArrayList<>();
            JSONArray jsonAccounts = response.getJSONArray("accounts");
            int lenAccounts = jsonAccounts.length();
            for (int i = 0; i < lenAccounts; i++) {
                JSONObject contact = new JSONObject(jsonAccounts.get(i).toString());
                UserAccount userAccount = new UserAccount(
                        contact.getString("name"),
                        contact.getString("value")
                );

                accounts.add(userAccount);
            }

            profileDto = new ProfileDto(
                    response.getLong("id"),
                    response.getString("username"),
                    response.getInt("project_id"),
                    response.getString("project"),
                    response.getString("fullname"),
                    response.getString("gender"),
                    response.getString("avatar_url"),
                    response.getString("main_group"),
                    response.getString("birthdate"),
                    response.getString("about"),
                    activity.getString("date_joined"),
                    activity.getString("last_seen"),
                    contacts,
                    groups,
                    accounts
            );
            return profileDto;

        } catch (InterruptedException | TimeoutException e) {
            System.out.println("Time out");
        } catch (ExecutionException e) {
            System.out.println("Invalid login or password");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public GroupDto requestGroupDto(long id) {
        final String url = projectUrl + "/api/mobile/v1/groups/" + id;

        RequestFuture<JSONObject> requestFuture = RequestFuture.newFuture();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(), requestFuture, error -> {
            if (error.networkResponse == null) {
                apiHelper.setMessage(NETWORK_ERROR_MESSAGE);
            } else {
                if (error.networkResponse.statusCode == 401) {
                    apiHelper.setMessage(RELOAD_REQUEST);
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                return getAuthHeader();
            }
        };
        request.setTag(TAG);
        queue.add(request);

        try {
            JSONObject response = requestFuture.get(1, TimeUnit.SECONDS);
            String name = response.getString("name");
            JSONArray students = response.getJSONArray("students");
            List<StudentDto> list = new ArrayList<>();
            for (int i = 0; i < students.length(); ++i) {
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
            //apiHelper.setMessage(NETWORK_ERROR_MESSAGE);
        } catch (ExecutionException e) {
            System.out.println("Update token");
        } catch (JSONException e) {
            System.out.println("Json creating failed");
        }
        return null;
    }


    @Override
    public List<NewsDto> requestMainNewsDto(Integer limit, Integer offset) {
        final List<NewsDto> newsDtoList = new ArrayList<>();
        final StringBuilder url = new StringBuilder(projectUrl).append("/api/mobile/v1/topics/main/?");
        url.append("limit=").append(limit).append("&offset=").append(offset);
        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(
                Request.Method.GET,
                url.toString(),
                null,
                future,
                error -> {
                    if (error.networkResponse != null && error.networkResponse.statusCode == 401) {
                        //Надо обновить токен!!!
                        //updateToken
                        //reload request
//                requestSchedulerItems();
                    }}) {
            @Override
            public Map<String, String> getHeaders() {
                return getAuthHeader();
            }
        };
        queue.add(jsonArrayRequest);
        try {
            JSONObject response = future.get(1, TimeUnit.SECONDS);
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
                String post_url = one_new.getString("url");

                newsDtoList.add(new NewsDto(
                        id,
                        fullname,
                        title,
                        blog,
                        publish_date,
                        avatar_url,
                        comments_count,
                        post_url
                ));
            }


        } catch (InterruptedException | TimeoutException e) {
            apiHelper.setMessage(NETWORK_ERROR_MESSAGE);
        } catch (ExecutionException e) {
            System.out.println("Update token");
        } catch (JSONException e) {
            System.out.println("Json creating failed");
        }
        return newsDtoList;
    }

    @Override
    public List<NewsDto> requestSubscribedNewsDto(Integer limit, Integer offset) {
        final List<NewsDto> newsDtoList = new ArrayList<>();
        final StringBuilder url = new StringBuilder(projectUrl).append("/api/mobile/v1/topics/subscribed/?");
        url.append("limit=").append(limit).append("&offset=").append(offset);
        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(
                Request.Method.GET,
                url.toString(),
                null,
                future,
                error -> {
                    if (error.networkResponse != null && error.networkResponse.statusCode == 401) {
                        //Надо обновить токен!!!
                        //updateToken
                        //reload request
//                requestSchedulerItems();
                    }}) {
            @Override
            public Map<String, String> getHeaders() {
                return getAuthHeader();
            }
        };
        queue.add(jsonArrayRequest);
        try {
            JSONObject response = future.get(1, TimeUnit.SECONDS);
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
                String post_url = one_new.getString("url");

                newsDtoList.add(new NewsDto(
                        id,
                        fullname,
                        title,
                        blog,
                        publish_date,
                        avatar_url,
                        comments_count,
                        post_url
                ));
            }


        } catch (InterruptedException | TimeoutException e) {
            apiHelper.setMessage(NETWORK_ERROR_MESSAGE);
        } catch (ExecutionException e) {
            System.out.println("Update token");
        } catch (JSONException e) {
            System.out.println("Json creating failed");
        }
        return newsDtoList;
    }


    private boolean flag = false;

    @Override
    public List<SchedulerItemDto> requestSchedulerItems() {

        ArrayList<SchedulerItemDto> items = new ArrayList<>();
        final String url = "https://polis.mail.ru/api/mobile/v1/schedule/";

        RequestFuture<JSONArray> requestFuture = RequestFuture.newFuture();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, new JSONArray(), requestFuture, error -> {
            if (error.networkResponse != null && error.networkResponse.statusCode == 401) {
                apiHelper.setMessage(RELOAD_REQUEST);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                return getAuthHeader();
            }
        };
        jsonArrayRequest.setTag(TAG);
        queue.add(jsonArrayRequest);

        if (flag) {
            user.setAuth_token("ggg");
        }
        flag = !flag;

        try {
            JSONArray response = requestFuture.get(1, TimeUnit.SECONDS);

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
        } catch (InterruptedException | TimeoutException e) {
            apiHelper.setMessage(NETWORK_ERROR_MESSAGE);
        } catch (ExecutionException e) {
            System.out.println("Update token");
        } catch (JSONException e) {
            System.out.println("Json creating failed");
        }
        return items;
    }

    @Override
    public SchedulerItemCheckInDto checkInSchedulerItem(long id) {
        final String url = "https://polis.mail.ru/api/mobile/v1/schedule/" + id + "/check/";
        JSONObject json = new JSONObject();

        RequestFuture<JSONObject> requestFuture = RequestFuture.newFuture();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, json, requestFuture, error -> {
            if (error.networkResponse == null) {
                apiHelper.setMessage(NETWORK_ERROR_MESSAGE);
            } else if (error.networkResponse.statusCode == 401) {
                apiHelper.setMessage(RELOAD_REQUEST);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                return getAuthHeader();
            }
        };
        request.setTag(TAG);
        queue.add(request);

        SchedulerItemCheckInDto schedulerItemCheckInDto = null;
        try {
            JSONObject response = requestFuture.get(1, TimeUnit.SECONDS);

            schedulerItemCheckInDto = new SchedulerItemCheckInDto(
                    response.getInt("schedule_item"),
                    response.getString("feedback_url")
            );
        } catch (TimeoutException e) {
            System.err.println("timeout");
        } catch (JSONException e) {
            int a = 5;
            System.err.println("jsonException");
        } catch (ExecutionException | InterruptedException e) {
            System.err.println("another");
            //unknown error
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
