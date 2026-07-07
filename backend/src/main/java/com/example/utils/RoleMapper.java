package com.example.utils;

import com.example.dto.RoleDTO;
import com.example.model.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    public RoleDTO toDTO(Role role) {
        if (role == null) {
            return null;
        }
        return new RoleDTO(role.getId(), role.getName(), role.getDisplayName());
    }
}
