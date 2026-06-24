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

    void insertUserRole(@Param("userId") Integer userId, @Param("roleId") Integer roleId);

    void deleteRolesForUser(Integer userId);
}
