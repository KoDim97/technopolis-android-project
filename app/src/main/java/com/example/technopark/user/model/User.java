package com.example.technopark.user.model;

public class User {
    private String login;
    private String password;
    private String auth_token;
    private Integer user_id;
    private String username;

    public User(){}

    public String getAuth_token() {
        return auth_token;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setAuth_token(String auth_token) {
        this.auth_token = auth_token;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
