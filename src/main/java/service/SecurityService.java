package service;

import dao.UserDao;
import model.Role;
import model.User;

import java.time.LocalDate;

public class SecurityService {
    private final UserDao userDao;

    public SecurityService(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean login(String login, String password) {
        User user = userDao.read(login);
        return user != null && user.getPassword().equals(password);
    }

    public boolean changePassword(String login, String oldPassword, String newPassword) {
        User user = userDao.read(login);
        if (user == null) {
            return false;
        }
        if (!user.getPassword().equals(oldPassword)) {
            return false;
        }
        user.setPassword(newPassword);
        return true;
    }

    private boolean register(String login,
                             String password,
                             String email,
                             String surname,
                             String name,
                             String patronymic,
                             LocalDate birthday,
                             Role role) {
        User user = userDao.read(login);
        if (user != null) {
            return false;
        }
        userDao.create(new User(login, password, email, surname, name, patronymic, birthday, role));
        return true;
    }
}
