package com.example.login_demo.dao;

import com.example.login_demo.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LoginMapper {
    User selectPassword(@Param("username") String username);
    Integer existUser(@Param("username") String username);
    List<User> selectAllUser();
    void insertUser(@Param("user") User user);
    void updatePassword(@Param("username") String username, @Param("newPassword") String newPassword);
    void deleteUser(@Param("username") String username);
}
