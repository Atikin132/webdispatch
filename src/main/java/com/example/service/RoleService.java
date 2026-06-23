package com.example.service;

import com.example.mapper.RoleMapper;
import com.example.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class RoleService {
    @Autowired
    private RoleMapper roleMapper;

    public Role findById(Integer id) {
        return roleMapper.findById(id);
    }

    public Set<Role> findAll() {
        return roleMapper.findAll();
    }

    public Set<Role> findByUserId(Integer userId) {
        return roleMapper.findByUserId(userId);
    }

    @Transactional
    public void saveRolesForUser(Integer userId, Set<Role> roles) {
        roleMapper.deleteRolesForUser(userId);

        if (roles != null && !roles.isEmpty()) {
            for (Role role : roles) {
                roleMapper.insertUserRole(userId, role.getId());
            }
        }
    }

    public void deleteRolesForUser(Integer userId) {
        roleMapper.deleteRolesForUser(userId);
    }
}
