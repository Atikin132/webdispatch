package com.example.service;

import com.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    @Autowired
    private UserService userService;

    public String login(String login, String password) {
        login = (login != null) ? login.trim() : null;
        password = (password != null) ? password.trim() : null;

        if (login == null || login.isBlank()) {
            return "Login cannot be empty";
        }
        if (password == null || password.isBlank()) {
            return "Password cannot be empty";
        }

        User user = userService.getUserByLogin(login);

        if (user != null && user.getPassword().equals(password)) {
            return null;
        } else {
            return "Wrong login or password";
        }
    }

    public boolean changePassword(Integer userId, String oldPassword, String newPassword) {
        User user = userService.getUser(userId);

        if (user == null) {
            return false;
        }
        if (!user.getPassword().equals(oldPassword)) {
            return false;
        }

        userService.updatePassword(userId, newPassword);
        return true;
    }
}
