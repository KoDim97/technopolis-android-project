package com.example.technopolis.user.service;

import com.example.technopolis.api.MailApi;
import com.example.technopolis.api.dto.AuthDto;
import com.example.technopolis.user.model.User;

public class AuthService {
    private final MailApi api;
    private User user;

    public AuthService(MailApi api, User user) {
        this.api = api;
        this.user = user;
    }

    public boolean CheckAuth(String login, String password) {
        AuthDto authDto = api.requestAuthDto(login, password);
        if (authDto != null) {
            user.setLogin(login);
            user.setPassword(password);
            user.setAuth_token(authDto.getAuth_token());
            user.setUser_id(authDto.getId());
            user.setUsername(authDto.getUsername());
            return true;
        } else {
            return false;
        }
    }

    public User getCurrentUser() {
        return user;
    }
}
