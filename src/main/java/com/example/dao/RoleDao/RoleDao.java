package com.example.dao.RoleDao;

import com.example.model.Role;

import java.sql.Connection;
import java.util.Set;

public interface RoleDao {
    Role findById(Integer id);

    Set<Role> findAll();

    Set<Role> findByUserId(Integer userId);

    void saveRolesForUser(Connection con, Integer userId, Set<Role> roles);

    void deleteRolesForUser(Connection con, Integer userId);
}
