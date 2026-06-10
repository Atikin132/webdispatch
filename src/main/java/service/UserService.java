package service;

import dao.UserDao;
import model.Role;
import model.User;

import java.time.LocalDate;
import java.util.Collection;

public class UserService {
    private static UserService INSTANCE;
    private final UserDao userDao;

    private UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public static UserService getInstance() {
        if (INSTANCE == null) {
            throw new IllegalStateException("UserService is not initialized");
        }
        return INSTANCE;
    }

    public static void init(UserDao userDao) {
        if (INSTANCE == null) {
            INSTANCE = new UserService(userDao);
        }
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

    public void updatePassword(String login, String newPassword) {
        userDao.updatePassword(login, newPassword);
    }

    public boolean existsByLogin(String login) {
        return userDao.read(login) != null;
    }

    public boolean isBirthdayBeforeNow(LocalDate birthday) {
        return birthday.isBefore(LocalDate.now());
    }

    public User createEmptyUser() {
        User user = new User();
        user.setRole(Role.USER);
        return user;
    }

    public String validateAndPrepareUser(User user,
                                         String birthdayStr,
                                         String roleStr,
                                         String oldLogin) {
        try {
            user.setBirthday(LocalDate.parse(birthdayStr));
        } catch (Exception e) {
            return "Invalid birthday format";
        }

        try {
            user.setRole(Role.valueOf(roleStr));
        } catch (Exception e) {
            return "Invalid role";
        }
        return (oldLogin != null) ? validateForForm(user, oldLogin) : validateForForm(user, null);
    }

    public String validateForForm(User user, String oldLogin) {
        String basicError = validateUser(user);
        if (basicError != null) {
            return basicError;
        }

        if (!isBirthdayBeforeNow(user.getBirthday())) {
            return "The date must not be today or in the future";
        }

        boolean isLoginTaken = (oldLogin != null) ? (!oldLogin.equals(user.getLogin()) &&
                existsByLogin(user.getLogin())) : existsByLogin(user.getLogin());
        if (isLoginTaken) {
            return "User with this login already exists";
        }

        return null;
    }

    public String validateUser(User user) {
        if (user.getLogin() == null || user.getLogin().trim().isEmpty()) {
            return "Login is required";
        }
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            return "Password must contain at least 6 characters";
        }
        if (user.getEmail() == null ||
                !user.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            return "Invalid email";
        }
        if (user.getSurname() == null || user.getSurname().trim().isEmpty()) {
            return "Surname is required";
        }
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            return "Name is required";
        }
        if (user.getPatronymic() == null || user.getPatronymic().trim().isEmpty()) {
            return "Patronymic is required";
        }
        if (user.getBirthday() == null) {
            return "Birthday is required";
        }
        return null;
    }
}
