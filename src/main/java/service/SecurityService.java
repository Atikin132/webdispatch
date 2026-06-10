package service;

import dao.UserDao;
import model.Role;
import model.User;

import java.time.LocalDate;

public class SecurityService {
    private final UserService userService;

    public SecurityService(UserService userService) {
        this.userService = userService;
    }


    public boolean login(String login, String password) {
        User user = userService.getUser(login);
        return user != null && user.getPassword().equals(password);
    }

    public boolean changePassword(String login, String oldPassword, String newPassword) {
        User user = userService.getUser(login);

        if (user == null) {
            return false;
        }
        if (!user.getPassword().equals(oldPassword)) {
            return false;
        }

        userService.updatePassword(login, newPassword);
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
        User user = userService.getUser(login);
        if (user != null) {
            return false;
        }
        userService.createUser(new User(login,
                password,
                email,
                surname,
                name,
                patronymic,
                birthday,
                role));
        return true;
    }
}
