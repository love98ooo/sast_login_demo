package com.example.login_demo.dao;

import com.example.login_demo.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LoginMapper {
    User selectUser(@Param("userName") String userName);
    Integer existUser(@Param("userName") String userName);
    List<User> selectAllUser();
    void insertUser(@Param("user") User user);
    void updatePassword(@Param("id") int id, @Param("newPassword") String newPassword);
    void updateUserName(@Param("id") int id, @Param("userName") String userName);
    void updateEmail(@Param("id") int id, @Param("email") String email);
    void deleteUser(@Param("userName") String userName);
}
