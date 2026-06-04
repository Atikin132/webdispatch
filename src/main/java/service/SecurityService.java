package service;

import model.Role;
import model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SecurityService {
    private final Map<String, User> users = new HashMap<>();

    public SecurityService() {
        users.put("u1",
                new User("u1",
                        "u1",
                        "u1@example.com",
                        "Petrov",
                        "Peter",
                        "Petrovich",
                        LocalDate.of(1990, 1, 1),
                        Role.USER));
        users.put("u2",
                new User("u2",
                        "u2",
                        "u2@example.com",
                        "Ivanov",
                        "Ivan",
                        "Ivanovich",
                        LocalDate.of(2004, 6, 25),
                        Role.USER));
        users.put("u3",
                new User("u3",
                        "u3",
                        "u3@example.com",
                        "Vasiliev",
                        "Vasili",
                        "Vasilievich",
                        LocalDate.of(1984, 5, 9),
                        Role.USER));

        users.put("admin1",
                new User("admin1",
                        "admin1",
                        "admin1@example.com",
                        "Adminov",
                        "Admin",
                        "Adminovich",
                        LocalDate.of(2000, 1, 1),
                        Role.ADMIN));
        users.put("admin2",
                new User("admin2",
                        "admin2",
                        "admin2@example.com",
                        "Antonov",
                        "Anton",
                        "Antonovich",
                        LocalDate.of(1999, 9, 9),
                        Role.ADMIN));
    }

    public boolean login(String login, String password) {
        User user = users.get(login);
        if (user == null) {
            return false;
        }
        return user.getPassword().equals(password);
    }

    private void register(String login,
                          String password,
                          String email,
                          String surname,
                          String name,
                          String patronymic,
                          LocalDate birthday,
                          Role role) {
        users.put(login,
                new User(login, password, email, surname, name, patronymic, birthday, role));
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

    public Collection<User> getAllUsers() {
        return users.values();
    }
}
