package dao.UserDao;

import model.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class InMemoryUserDao implements UserDao {
    private static InMemoryUserDao INSTANCE;

    private final Map<String, User> users = new HashMap<>();

    private InMemoryUserDao() {
        users.put("u1",
                new User(1,
                        "u1",
                        "111111",
                        "Peter",
                        LocalDate.of(1990, 1, 1),
                        36,
                        BigDecimal.valueOf(3000)));
        users.put("u2",
                new User(2,
                        "u2",
                        "222222",
                        "Ivan",
                        LocalDate.of(2004, 6, 18),
                        22,
                        BigDecimal.valueOf(5000)));
        users.put("u3",
                new User(3,
                        "u3",
                        "333333",
                        "Vasili",
                        LocalDate.of(1984, 5, 9),
                        42,
                        BigDecimal.valueOf(2000)));
        users.put("admin1",
                new User(4,
                        "admin1",
                        "admin1",
                        "Admin",
                        LocalDate.of(2000, 1, 1),
                        26,
                        BigDecimal.valueOf(8000)));
        users.put("admin2",
                new User(5,
                        "admin2",
                        "admin2",
                        "Anton",
                        LocalDate.of(1999, 6, 9),
                        27,
                        BigDecimal.valueOf(8000)));
    }

    static InMemoryUserDao getInstance() {
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
