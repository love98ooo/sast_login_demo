package com.example.login_demo.service;

import com.example.login_demo.pojo.User;

import java.util.List;

public interface LoginService {
    User findUser(String username, String userPassword);
    User findUserInfo(String userName);
    String findUsernameByToken(String token);
    String addUser(User user);
    String changeUserInfo(User user, String token);
    Boolean checkPassword(String username, String password);
    List<User> findAllUser();
    void deleteUser(String username);
}
