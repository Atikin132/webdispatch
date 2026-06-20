package service;

import dao.RoleDao.RoleDao;
import dao.UserDao.UserDao;
import model.Role;
import model.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class UserService {
    private static UserService INSTANCE;
    private final UserDao userDao;
    private final RoleDao roleDao;

    private UserService(UserDao userDao, RoleDao roleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
    }

    static UserService getInstance() {
        if (INSTANCE == null) {
            throw new IllegalStateException("UserService is not initialized");
        }
        return INSTANCE;
    }

    static void init(UserDao userDao, RoleDao roleDao) {
        if (INSTANCE == null) {
            INSTANCE = new UserService(userDao, roleDao);
        }
    }

    static boolean isInitialized() {
        return INSTANCE != null;
    }

    public Collection<User> getAllUsers() {
        Collection<User> users = userDao.findAll();
        for (User user : users) {
            user.setRoles(roleDao.findByUserId(user.getId()));
        }
        return users;
    }

    public User getUser(Integer id) {
        User user = userDao.read(id);
        if (user != null) {
            user.setRoles(roleDao.findByUserId(user.getId()));
        }
        return user;
    }

    public User getUserByLogin(String login) {
        User user = userDao.findByLogin(login);
        if (user != null) {
            user.setRoles(roleDao.findByUserId(user.getId()));
        }
        return user;
    }

    public void createUser(User user) {
        userDao.create(user);
        roleDao.saveRolesForUser(user.getId(), user.getRoles());
    }

    public void updateUser(Integer id, User updatedUser) {
        userDao.update(id, updatedUser);
        roleDao.deleteRolesForUser(id);
        roleDao.saveRolesForUser(id, updatedUser.getRoles());
    }

    public void deleteUser(Integer id) {
        userDao.delete(id);
    }

    public void updatePassword(Integer id, String newPassword) {
        userDao.updatePassword(id, newPassword);
    }

    public boolean isBirthdayBeforeNow(LocalDate birthday) {
        return birthday.isBefore(LocalDate.now());
    }

    public User createEmptyUser() {
        return new User();
    }

    public String validateAndPrepareUser(User user,
                                         String idStr,
                                         String birthdayStr,
                                         String salaryStr,
                                         String[] selectedRoleIds) {
        if (!idStr.isEmpty()) {
            user.setId(Integer.parseInt(idStr));
        }

        prepareRoles(user, selectedRoleIds);

        try {
            user.setBirthday(LocalDate.parse(birthdayStr));
        } catch (Exception e) {
            return "Invalid birthday format";
        }

        try {
            user.setSalary(new BigDecimal(salaryStr));
        } catch (Exception e) {
            return "Invalid salary";
        }

        user.setAge(Period.between(user.getBirthday(), LocalDate.now()).getYears());

        return validateForForm(user);
    }

    private void prepareRoles(User user, String[] selectedRoleIds) {
        Set<Role> roles = new HashSet<>();

        if (selectedRoleIds != null) {
            for (String roleIdStr : selectedRoleIds) {

                Role role = roleDao.findById(Integer.parseInt(roleIdStr));

                if (role != null) {
                    roles.add(role);
                }
            }
        }

        user.setRoles(roles);
    }

    public String validateForForm(User user) {
        String basicError = validateUser(user);
        if (basicError != null) {
            return basicError;
        }

        if (!isBirthdayBeforeNow(user.getBirthday())) {
            return "The date must not be today or in the future";
        }

        User existingUser = userDao.findByLogin(user.getLogin().trim());

        if (existingUser != null && !existingUser.getId().equals(user.getId())) {
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
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            return "Name is required";
        }
        if (user.getBirthday() == null) {
            return "Birthday is required";
        }
        if (user.getSalary() == null || user.getSalary().compareTo(BigDecimal.ZERO) < 0) {
            return "Salary cannot be negative or null";
        }
        return null;
    }
}
