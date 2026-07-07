package com.example.utils;

import com.example.dto.RoleDTO;
import com.example.dto.UserDTO;
import com.example.dto.UserRequest;
import com.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;

@Component
public class UserMapper {

    @Autowired
    private RoleMapper roleMapper;

    public User fromRequest(UserRequest request) {
        return new User(request.getId(),
                request.getLogin() != null ? request.getLogin().trim() : null,
                request.getPassword() != null ? request.getPassword().trim() : null,
                request.getName() != null ? request.getName().trim() : null,
                request.getBirthDate(),
                request.getAge(),
                request.getSalary(),
                new HashSet<>());
    }

    public UserDTO toDTO(User user) {
        List<RoleDTO> roleDTOs = user.getRoles().stream().map(roleMapper::toDTO).toList();
        return new UserDTO(user.getId(),
                user.getLogin(),
                user.getPassword(),
                user.getName(),
                user.getBirthDate(),
                user.getAge(),
                user.getSalary(),
                roleDTOs);
    }

}