package com.example.login_demo.service;

import com.example.login_demo.pojo.User;

import java.util.List;

public interface LoginService {
    String findUser(String username, String userPassword);
    String findUsernameByToken(String token);
    String addUser(User user);
    String changeUserPassword(String username, String newPassword);
    Boolean checkPassword(String username, String password);
    List<User> findAllUser();
    void deleteUser(String username);
}
