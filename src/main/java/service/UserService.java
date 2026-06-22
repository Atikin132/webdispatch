package service;

import dao.UserDao.UserDao;
import model.Role;
import model.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class UserService {
    private static UserService INSTANCE;
    private final UserDao userDao;
    private final RoleService roleService;

    private UserService(UserDao userDao, RoleService roleService) {
        this.userDao = userDao;
        this.roleService = roleService;
    }

    static UserService getInstance() {
        if (INSTANCE == null) {
            throw new IllegalStateException("UserService is not initialized");
        }
        return INSTANCE;
    }

    static void init(UserDao userDao, RoleService roleService) {
        if (INSTANCE == null) {
            INSTANCE = new UserService(userDao, roleService);
        }
    }

    static boolean isInitialized() {
        return INSTANCE != null;
    }

    public Collection<User> getAllUsers() {
        Collection<User> users = userDao.findAll();
        for (User user : users) {
            user.setRoles(roleService.findByUserId(user.getId()));
        }
        return users;
    }

    public User getUser(Integer id) {
        User user = userDao.read(id);
        if (user != null) {
            user.setRoles(roleService.findByUserId(user.getId()));
        }
        return user;
    }

    public User getUserByLogin(String login) {
        User user = userDao.findByLogin(login);
        if (user != null) {
            user.setRoles(roleService.findByUserId(user.getId()));
        }
        return user;
    }

    public void createUser(User user) {
        userDao.create(user);
        roleService.saveRolesForUser(user.getId(), user.getRoles());
    }

    public void updateUser(Integer id, User updatedUser) {
        userDao.update(id, updatedUser);
        roleService.deleteRolesForUser(id);
        roleService.saveRolesForUser(id, updatedUser.getRoles());
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
                                         String ageStr,
                                         String salaryStr,
                                         String[] selectedRoleIds) {
        if (!idStr.isEmpty()) {
            user.setId(Integer.parseInt(idStr));
        }

        prepareRoles(user, selectedRoleIds);

        if (birthdayStr != null && !birthdayStr.isBlank()) {
            try {
                user.setBirthday(LocalDate.parse(birthdayStr));
            } catch (Exception e) {
                return "Invalid birthday format";
            }
        }

        try {
            user.setAge(Integer.parseInt(ageStr));
        } catch (Exception e) {
            return "Invalid age";
        }

        try {
            user.setSalary(new BigDecimal(salaryStr));
        } catch (Exception e) {
            return "Invalid salary";
        }

        return validateForForm(user);
    }

    private void prepareRoles(User user, String[] selectedRoleIds) {
        Set<Role> roles = new HashSet<>();

        if (selectedRoleIds != null) {
            for (String roleIdStr : selectedRoleIds) {

                Role role = roleService.findById(Integer.parseInt(roleIdStr));

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

        if (user.getBirthday() != null && !isBirthdayBeforeNow(user.getBirthday())) {
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
        if (user.getLogin().length() > 50) {
            return "Login cannot be longer than 50 characters";
        }
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            return "Password must contain at least 6 characters";
        }
        if (user.getPassword().length() > 255) {
            return "Password is too long";
        }
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            return "Name is required";
        }
        if (user.getName().length() > 100) {
            return "Name cannot be longer than 100 characters";
        }
        if (user.getAge() == null) {
            return "Age is required";
        }
        if (user.getAge() <= 18) {
            return "User must be older than 18";
        }
        if (user.getSalary() == null) {
            return "Salary is required";
        }
        if (user.getSalary().compareTo(BigDecimal.ZERO) < 0) {
            return "Salary cannot be negative";
        }
        if (user.getSalary().scale() > 2) {
            return "Salary can have max 2 decimal places";
        }
        if (user.getSalary().compareTo(new BigDecimal("99999999.99")) > 0) {
            return "Salary is too large";
        }
        return null;
    }
}
