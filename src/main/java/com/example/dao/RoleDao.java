package com.example.dao;

import com.example.model.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

@Mapper
public interface RoleDao {
    Role findById(Integer id);

    Set<Role> findAll();

    Set<Role> findByUserId(Integer userId);

    void insertUserRoles(@Param("userId") Integer userId, @Param("roles") Set<Role> roles);

    void deleteRolesForUser(Integer userId);
}
