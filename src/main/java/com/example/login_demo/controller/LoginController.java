package com.example.login_demo.controller;

import com.example.login_demo.annotation.AuthHandle;
import com.example.login_demo.common.BizException;
import com.example.login_demo.common.ResponseData;
import com.example.login_demo.common.ResponseEnum;
import com.example.login_demo.enums.UserEnum;
import com.example.login_demo.pojo.User;
import com.example.login_demo.service.LoginService;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class LoginController {
    private final LoginService loginService;

    private final String zeroInSha256 = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseData<Object> loginGate(@RequestBody MultiValueMap<String, String> params) {
        try {
            User response = loginService.findUser(params.get("userName").get(0), params.get("password").get(0));
            return ResponseData.Success(response);
        } catch (BizException e) {
            return ResponseData.Create(e);
        }
    }

    @AuthHandle(value = {UserEnum.User, UserEnum.Admin})
    @PostMapping("/user/register")
    public ResponseData<Object> uploadUser(HttpServletRequest request) {
        MultipartHttpServletRequest params = ((MultipartHttpServletRequest) request);
        String userName = params.getParameter("userName");
        String password = params.getParameter("password");
        String email = params.getParameter("email");
        User user = new User(userName, password, email);
        if (user.getUserName().equals("") || user.getPassword().equals(zeroInSha256)) {
            return ResponseData.Error("用户名或密码不能为空");
        }
        try {
            String response = loginService.addUser(user);
            return ResponseData.Success(response);
        } catch (BizException e) {
            return ResponseData.Create(e);
        }
    }

    @AuthHandle(value = {UserEnum.User, UserEnum.Admin})
    @GetMapping("/user/info")
    public ResponseData<Object> getMyInfo(@RequestHeader("Token") String token) {
        User user = new User();
        try {
            user.setUserName(loginService.findUsernameByToken(token));
            return ResponseData.Success(user);
        } catch (Exception e) {
            return ResponseData.Create(new BizException(ResponseEnum.UNEXPECTED));
        }
    }

    @AuthHandle(value = {UserEnum.User, UserEnum.Admin})
    @PostMapping("/user/edit_info")
    public ResponseData<Object> changeInfo(@RequestHeader("Token") String token,
                                           HttpServletRequest request) {
        try {
            MultipartHttpServletRequest params = ((MultipartHttpServletRequest) request);
            String userName = params.getParameter("userName");
            String password = params.getParameter("password");
            String email = params.getParameter("email");
            User user = new User(userName, password, email);
            String response = loginService.changeUserInfo(user, token);
            return ResponseData.Success(response);
        } catch (BizException e) {
            return ResponseData.Create(e);
        }
    }

    @AuthHandle(value = {UserEnum.Admin})
    @GetMapping("/admin/show_all")
    public ResponseData<Object> getAllUserInfo() {
        try {
            List<User> users = loginService.findAllUser();
            return ResponseData.Success(users);
        } catch (Exception e) {
            return ResponseData.Create(new BizException(ResponseEnum.UNEXPECTED));
        }
    }

    @AuthHandle(value = {UserEnum.Admin})
    @GetMapping("/admin/find_user_info")
    public ResponseData<Object> getUserInfo(String userName) {
        try {
            User user = loginService.findUserInfo(userName);
            return ResponseData.Success(user);
        } catch (Exception e) {
            return ResponseData.Create(new BizException(ResponseEnum.UNEXPECTED));
        }
    }

    @AuthHandle(value = {UserEnum.Admin})
    @PostMapping("/admin/del_user")
    public ResponseData<Object> deleteUser(HttpServletRequest request) {
        try {
            MultipartHttpServletRequest params = ((MultipartHttpServletRequest) request);
            String userName = params.getParameter("userName");
            loginService.deleteUser(userName);
            return ResponseData.Success("删除成功");
        } catch (Exception e) {
            return ResponseData.Create(new BizException(ResponseEnum.UNEXPECTED));
        }
    }
}
