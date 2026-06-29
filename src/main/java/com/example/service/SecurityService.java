package com.example.service;

import com.example.model.CustomUserDetails;
import com.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toList());
        return new CustomUserDetails(user.getId(),
                user.getLogin(),
                user.getPassword(),
                authorities);
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
