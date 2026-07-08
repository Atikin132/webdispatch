package com.example.dto;

import java.util.List;

public class JWTResponse {
    private String token;
    private String login;
    private List<String> roles;

    public JWTResponse(String token, String login, List<String> roles) {
        this.token = token;
        this.login = login;
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public String getLogin() {
        return login;
    }

    public List<String> getRoles() {
        return roles;
    }
}
