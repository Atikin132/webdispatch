package dao;

import model.Role;
import model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class InMemoryUserDao implements UserDao {
    private static InMemoryUserDao INSTANCE;

    private final Map<String, User> users = new HashMap<>();

    private InMemoryUserDao() {
        users.put("u1",
                new User("u1",
                        "111111",
                        "u1@example.com",
                        "Petrov",
                        "Peter",
                        "Petrovich",
                        LocalDate.of(1990, 1, 1),
                        Role.USER));
        users.put("u2",
                new User("u2",
                        "222222",
                        "u2@example.com",
                        "Ivanov",
                        "Ivan",
                        "Ivanovich",
                        LocalDate.of(2004, 6, 25),
                        Role.USER));
        users.put("u3",
                new User("u3",
                        "333333",
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

    public static InMemoryUserDao getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new InMemoryUserDao();
        }
        return INSTANCE;
    }

    @Override
    public void create(User user) {
        users.put(user.getLogin(), user);
    }

    @Override
    public void update(String oldLogin, User user) {
        users.remove(oldLogin);
        users.put(user.getLogin(), user);
    }

    @Override
    public User read(String login) {
        return users.get(login);
    }

    @Override
    public void delete(String login) {
        users.remove(login);
    }

    @Override
    public Collection<User> findAll() {
        return users.values();
    }

    @Override
    public void updatePassword(String login, String newPassword) {
        users.get(login).setPassword(newPassword);
    }
}
