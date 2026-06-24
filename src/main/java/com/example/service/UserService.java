package com.example.service;

import com.example.dao.UserDao;
import com.example.model.Role;
import com.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleService roleService;

    @Transactional
    public void createUser(User user) {
        userDao.create(user);
        roleService.saveRolesForUser(user.getId(), user.getRoles());
    }

    public User getUser(Integer id) {
        User user = userDao.read(id);
        if (user != null) {
            user.setRoles(roleService.findByUserId(user.getId()));
        }
        return user;
    }

    @Transactional
    public void updateUser(User updatedUser) {
        userDao.update(updatedUser);
        roleService.saveRolesForUser(updatedUser.getId(), updatedUser.getRoles());
    }

    @Transactional
    public void deleteUser(Integer id) {
        roleService.deleteRolesForUser(id);
        userDao.delete(id);
    }

    public Collection<User> getAllUsers() {
        Collection<User> users = userDao.findAll();
        for (User user : users) {
            user.setRoles(roleService.findByUserId(user.getId()));
        }
        return users;
    }

    public void updatePassword(Integer id, String newPassword) {
        userDao.updatePassword(id, newPassword);
    }

    public User getUserByLogin(String login) {
        User user = userDao.findByLogin(login);
        if (user != null) {
            user.setRoles(roleService.findByUserId(user.getId()));
        }
        return user;
    }

    public boolean isBirthDateBeforeNow(LocalDate birthDate) {
        return birthDate.isBefore(LocalDate.now());
    }

    public User createEmptyUser() {
        return new User();
    }

    public String validateAndPrepareUser(User user,
                                         String idStr,
                                         String birthDateStr,
                                         String ageStr,
                                         String salaryStr,
                                         String[] selectedRoleIds) {
        if (!idStr.isEmpty()) {
            user.setId(Integer.parseInt(idStr));
        }

        prepareRoles(user, selectedRoleIds);

        if (birthDateStr != null && !birthDateStr.isBlank()) {
            try {
                user.setBirthDate(LocalDate.parse(birthDateStr));
            } catch (Exception e) {
                return "Invalid birth date format";
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

        if (user.getBirthDate() != null && !isBirthDateBeforeNow(user.getBirthDate())) {
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
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            return "At least one role must be selected";
        }
        return null;
    }
}
