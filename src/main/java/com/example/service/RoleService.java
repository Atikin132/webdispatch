package com.example.service;

import com.example.dao.RoleDao;
import com.example.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class RoleService {
    @Autowired
    private RoleDao roleDao;

    public Role findById(Integer id) {
        return roleDao.findById(id);
    }

    public Set<Role> findAll() {
        return roleDao.findAll();
    }

    public Set<Role> findByUserId(Integer userId) {
        return roleDao.findByUserId(userId);
    }

    @Transactional
    public void saveRolesForUser(Integer userId, Set<Role> roles) {
        roleDao.deleteRolesForUser(userId);

        if (roles != null && !roles.isEmpty()) {
            for (Role role : roles) {
                roleDao.insertUserRole(userId, role.getId());
            }
        }
    }
}
