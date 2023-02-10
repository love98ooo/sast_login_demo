package com.example.login_demo.controller;

import com.example.login_demo.annotation.AuthHandle;
import com.example.login_demo.common.BizException;
import com.example.login_demo.common.ResponseData;
import com.example.login_demo.common.ResponseEnum;
import com.example.login_demo.enums.UserEnum;
import com.example.login_demo.pojo.User;
import com.example.login_demo.service.LoginService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LoginController {
    private final LoginService loginService;

    private final String zeroInSha256 = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/login")
    public ResponseData<Object> loginGate(String username, String password) {
        try {
            String response = loginService.findUser(username, password);
            return ResponseData.Success("success", response);
        } catch (BizException e) {
            return ResponseData.Create(e);
        }
    }

    @AuthHandle(value = {UserEnum.Admin})
    @PutMapping("/updateLoginInfo/createNewUser")
    public ResponseData<Object> uploadUser(@RequestBody User user) {
        if (user.getUsername().equals("") || user.getPassword().equals(zeroInSha256)) {
            return ResponseData.Error("用户名或密码错误");
        }
        String response = null;
        try {
            response = loginService.addUser(user);
        } catch (BizException e) {
            return ResponseData.Create(e);
        }
        return ResponseData.Success(response);
    }

    @PutMapping("/updateLoginInfo/changePassword")
    public ResponseData<Object> changePassword(@RequestHeader("Token") String token, String password, String newPassword) {
        if (newPassword.equals(zeroInSha256)) {
            return ResponseData.Error("请输入新密码");
        }
        String tokenHead = "token_";
        String username = loginService.findUsernameByToken(tokenHead + token);
        if (loginGateWithoutReturnToken(username, password)) {
            String response = null;
            try {
                response = loginService.changeUserPassword(username, newPassword);
            } catch (BizException e) {
                return ResponseData.Create(e);
            }
            return ResponseData.Success(response);
        }
        return ResponseData.Error("密码错误");
    }

    private boolean loginGateWithoutReturnToken(String username, String userPassword) {
        return loginService.checkPassword(username, userPassword);
    }

    @AuthHandle(value = {UserEnum.Admin})
    @GetMapping("/getAllUserInfo")
    public ResponseData<Object> getAllUserInfo() {
        try {
            List<User> users = loginService.findAllUser();
            return ResponseData.Success("Success", users);
        } catch (Exception e) {
            return ResponseData.Create(new BizException(ResponseEnum.UNEXPECTED));
        }
    }

    @AuthHandle(value = {UserEnum.Admin})
    @PostMapping("/deleteUser")
    public ResponseData<Object> deleteUser(String username) {
        try {
            loginService.deleteUser(username);
            return ResponseData.Success("删除成功");
        } catch (Exception e) {
            return ResponseData.Create(new BizException(ResponseEnum.UNEXPECTED));
        }
    }
}
