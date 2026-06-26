package com.example.service;

import com.example.model.Role;
import com.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static org.springframework.security.core.userdetails.User.withUsername;

@Service
public class SecurityService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userService.getUserByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException(login);
        }
        String[] roles = new String[user.getRoles().size()];
        int i = 0;
        for (Role role : user.getRoles()) {
            roles[i++] = String.valueOf(role.getName());
        }
        return withUsername(user.getLogin()).password(user.getPassword()).roles(roles).build();
    }

    public boolean changePassword(Integer userId, String oldPassword, String newPassword) {
        User user = userService.getUser(userId);

        if (user == null) {
            return false;
        }
        if (!user.getPassword().equals(oldPassword)) {
            return false;
        }

        userService.updatePassword(userId, newPassword);
        return true;
    }
}
