package service;

import model.User;

public class SecurityService {
    private static SecurityService INSTANCE;
    private final UserService userService;

    private SecurityService(UserService userService) {
        this.userService = userService;
    }

    public static SecurityService getInstance(UserService userService) {
        if (INSTANCE == null) {
            INSTANCE = new SecurityService(userService);
        }
        return INSTANCE;
    }

    public String login(String login, String password) {
        login = (login != null) ? login.trim() : null;
        password = (password != null) ? password.trim() : null;

        if (login == null || login.isBlank()) {
            return "Login cannot be empty";
        }
        if (password == null || password.isBlank()) {
            return "Password cannot be empty";
        }

        User user = userService.getUser(login);

        if (user != null && user.getPassword().equals(password)) {
            return null;
        } else {
            return "Wrong login or password";
        }
    }

    public boolean changePassword(String login, String oldPassword, String newPassword) {
        User user = userService.getUser(login);

        if (user == null) {
            return false;
        }
        if (!user.getPassword().equals(oldPassword)) {
            return false;
        }

        userService.updatePassword(login, newPassword);
        return true;
    }
}
