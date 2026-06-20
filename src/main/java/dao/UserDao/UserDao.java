package dao.UserDao;

import model.User;

import java.util.Collection;

public interface UserDao {

    void create(User user);

    void update(String oldLogin, User user);

    User read(String login);

    void delete(String login);

    Collection<User> findAll();

    void updatePassword(String login, String newPassword);
}
