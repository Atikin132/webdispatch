package service;

import model.User;

import java.util.HashMap;
import java.util.Map;

public class SecurityService {
    private final Map<String, User> users = new HashMap<>();

    public SecurityService() {
        users.put("u1", new User("u1", "u1"));
        users.put("u2", new User("u2", "u2"));
        users.put("u3", new User("u3", "u3"));
    }

    public boolean login(String login, String password) {
        User user = users.get(login);
        if (user == null) {
            return false;
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
