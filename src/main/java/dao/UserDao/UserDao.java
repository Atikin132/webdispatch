package dao.UserDao;

import model.User;

import java.util.Collection;

public interface UserDao {

    void create(User user);

    void update(Integer id, User user);

    User read(Integer id);

    void delete(Integer id);

    Collection<User> findAll();

    void updatePassword(Integer id, String newPassword);

    User findByLogin(String login);
}