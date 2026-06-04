package service;

import model.User;

import java.util.HashMap;
import java.util.Map;

public class SecurityService {
    private final Map<String, User> users = new HashMap<>();

    public boolean login(String login, String password) {
        User user = users.get(login);
        if (user == null) {
            register(login, password);
            return true;
        }
        return user.getPassword().equals(password);
    }

    private void register(String login, String password) {
        users.put(login, new User(login, password));
    }

    public User getUser(String login) {
        return users.get(login);
    }

    public boolean changePassword(String login, String oldPassword, String newPassword) {
        User user = users.get(login);
        if (!user.getPassword().equals(oldPassword)) {
            return false;
        }
        user.setPassword(newPassword);
        return true;
    }
}
