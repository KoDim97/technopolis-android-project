package com.example.technopolis.api.dto;

public class AuthDto {
    private final String auth_token;
    private final Integer id;
    private final String username;

    public AuthDto(String auth_token, Integer id, String username) {
        this.auth_token = auth_token;
        this.id = id;
        this.username = username;
    }

    public String getAuth_token() {
        return auth_token;
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}
