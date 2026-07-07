package com.example.service;

import com.example.dao.UserDao;
import com.example.model.Role;
import com.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private MessageSource messageSource;

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

    public void deleteUser(Integer id) {
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

    public String validateAndPrepareUser(User user, String[] selectedRoleIds) {
        prepareRoles(user, selectedRoleIds);
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
        if (user.getBirthDate() != null && !isBirthDateBeforeNow(user.getBirthDate())) {
            return messageSource.getMessage("validationBirthDateFuture",
                    null,
                    LocaleContextHolder.getLocale());
        }

        User existingUser = userDao.findByLogin(user.getLogin().trim());

        if (existingUser != null && !existingUser.getId().equals(user.getId())) {
            return messageSource.getMessage("userAlreadyExists",
                    null,
                    LocaleContextHolder.getLocale());
        }
        return null;
    }
}
