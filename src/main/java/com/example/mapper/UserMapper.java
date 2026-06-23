package com.example.mapper;

import com.example.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

@Mapper
public interface UserMapper {
    void create(User user);

    User read(Integer id);

    void update(@Param("id") Integer id, @Param("user") User user);

    void delete(Integer id);

    Collection<User> findAll();

    void updatePassword(@Param("id") Integer id, @Param("newPassword") String newPassword);

    User findByLogin(String login);
}
