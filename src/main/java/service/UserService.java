package service;

import dao.UserDao;
import model.User;

import java.time.LocalDate;
import java.util.Collection;

public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public Collection<User> getAllUsers() {
        return userDao.findAll();
    }

    public User getUser(String login) {
        return userDao.read(login);
    }

    public void createUser(User user) {
        userDao.create(user);
    }

    public void updateUser(String oldLogin, User updatedUser) {
        userDao.update(oldLogin, updatedUser);
    }

    public void deleteUser(String login) {
        userDao.delete(login);
    }

    public boolean existsByLogin(String login) {
        return userDao.read(login) != null;
    }

    public boolean isBirthdayBeforeNow(LocalDate birthday) {
        return birthday.isBefore(LocalDate.now());
    }
}
