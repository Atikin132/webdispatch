package com.example.utils;

import com.example.dto.RoleDTO;
import com.example.dto.UserDTO;
import com.example.dto.UserFormDTO;
import com.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;

@Component
public class UserMapper {

    @Autowired
    private RoleMapper roleMapper;

    public User fromDTO(UserFormDTO dto) {
        return new User(dto.getId(),
                dto.getLogin() != null ? dto.getLogin().trim() : null,
                dto.getPassword() != null ? dto.getPassword().trim() : null,
                dto.getName() != null ? dto.getName().trim() : null,
                dto.getBirthDate(),
                dto.getAge(),
                dto.getSalary(),
                new HashSet<>());
    }

    public UserDTO toDTO(User user) {
        List<RoleDTO> roleDTOs =
                user.getRoles().stream().map(roleMapper::toDTO).toList();
        return new UserDTO(user.getId(),
                user.getLogin(),
                null,
                user.getName(),
                user.getBirthDate(),
                user.getAge(),
                user.getSalary(),
                roleDTOs);
    }

}