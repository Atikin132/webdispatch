package dao.UserDao;

import model.User;

import java.math.BigDecimal;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class InMemoryUserDao implements UserDao {
    private static InMemoryUserDao INSTANCE;

    private final Map<Integer, User> users = new HashMap<>();

    private int nextId = 6;

    private InMemoryUserDao() {
        users.put(1,
                new User(1,
                        "u1",
                        "111111",
                        "Peter",
                        LocalDate.of(1990, 1, 1),
                        36,
                        BigDecimal.valueOf(3000)));
        users.put(2,
                new User(2,
                        "u2",
                        "222222",
                        "Ivan",
                        LocalDate.of(2004, 6, 18),
                        22,
                        BigDecimal.valueOf(5000)));
        users.put(3,
                new User(3,
                        "u3",
                        "333333",
                        "Vasili",
                        LocalDate.of(1984, 5, 9),
                        42,
                        BigDecimal.valueOf(2000)));
        users.put(4,
                new User(4,
                        "admin1",
                        "admin1",
                        "Admin",
                        LocalDate.of(2000, 1, 1),
                        26,
                        BigDecimal.valueOf(8000)));
        users.put(5,
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
    public void create(Connection con, User user) {

    }

    @Override
    public void create(User user) {
        user.setId(nextId++);
        users.put(user.getId(), user);
    }

    @Override
    public void update(Integer id, User user) {
        users.put(id, user);
    }

    @Override
    public void update(Connection con, Integer id, User user) {

    }

    @Override
    public User read(Integer id) {
        return users.get(id);
    }

    @Override
    public void delete(Integer id) {
        users.remove(id);
    }

    @Override
    public Collection<User> findAll() {
        return users.values();
    }

    @Override
    public void updatePassword(Integer id, String newPassword) {
        User user = users.get(id);
        if (user != null) {
            user.setPassword(newPassword);
        }
    }

    @Override
    public User findByLogin(String login) {
        return users.values().stream().filter(user -> user.getLogin().equals(login)).findFirst()
                .orElse(null);
    }
}
