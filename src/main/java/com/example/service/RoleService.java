package com.example.service;

import com.example.dao.RoleDao.RoleDao;
import com.example.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
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

    public void saveRolesForUser(Connection con, Integer userId, Set<Role> roles) {
        roleDao.saveRolesForUser(con, userId, roles);
    }

    public void deleteRolesForUser(Connection con, Integer userId) {
        roleDao.deleteRolesForUser(con, userId);
    }
}
