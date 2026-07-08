package com.example.dto;

import java.util.List;

public class JWTResponse {
    private String token;
    private Integer id;
    private String login;
    private List<String> roles;

    public JWTResponse(String token, Integer id, String login, List<String> roles) {
        this.token = token;
        this.id = id;
        this.login = login;
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public Integer getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public List<String> getRoles() {
        return roles;
    }
}
