package com.example.login_demo.controller;

import com.example.login_demo.annotation.AuthHandle;
import com.example.login_demo.common.ResponseData;
import com.example.login_demo.enums.UserEnum;
import com.example.login_demo.model.ResponseLogin;
import com.example.login_demo.model.ResponseUserInfo;
import com.example.login_demo.pojo.User;
import com.example.login_demo.service.LoginService;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class LoginController {
    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseData<Object> loginGate(@RequestBody MultiValueMap<String, String> params) {
        ResponseLogin response = loginService.findUser(params.get("userName").get(0), params.get("password").get(0));
        return ResponseData.Success(response);
    }

    @AuthHandle(value = {UserEnum.User, UserEnum.Admin})
    @PostMapping("/user/register")
    public ResponseData<Object> uploadUser(HttpServletRequest request) {
        MultipartHttpServletRequest params = ((MultipartHttpServletRequest) request);
        String userName = params.getParameter("userName");
        String password = params.getParameter("password");
        String email = params.getParameter("email");
        User user = new User(userName, password, email);
        String nullInSha256 = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";
        if (user.getUserName().equals("") || user.getPassword().equals(nullInSha256)) {
            return ResponseData.Error("用户名或密码不能为空");
        }
        String response = loginService.addUser(user);
        return ResponseData.Success(response);
    }

    @AuthHandle(value = {UserEnum.User, UserEnum.Admin})
    @GetMapping("/user/info")
    public ResponseData<Object> getMyInfo(@RequestHeader("Token") String token) {
        ResponseUserInfo responseUserInfo = new ResponseUserInfo();
        responseUserInfo.setUserName(loginService.findUsernameByToken(token));
        return ResponseData.Success(responseUserInfo);
    }

    @AuthHandle(value = {UserEnum.User, UserEnum.Admin})
    @PostMapping("/user/edit_info")
    public ResponseData<Object> changeInfo(@RequestHeader("Token") String token,
                                           HttpServletRequest request) {
        MultipartHttpServletRequest params = ((MultipartHttpServletRequest) request);
        String userName = params.getParameter("userName");
        String password = params.getParameter("password");
        String email = params.getParameter("email");
        User user = new User(userName, password, email);
        String response = loginService.changeUserInfo(user, token);
        return ResponseData.Success(response);
    }

    @AuthHandle(value = {UserEnum.Admin})
    @GetMapping("/admin/show_all")
    public ResponseData<Object> getAllUserInfo() {
        List<ResponseUserInfo> users = loginService.findAllUser();
        return ResponseData.Success(users);
    }

    @AuthHandle(value = {UserEnum.Admin})
    @GetMapping("/admin/find_user_info")
    public ResponseData<Object> getUserInfo(String userName) {
        ResponseUserInfo responseUserInfo = new ResponseUserInfo(loginService.findUserInfo(userName));
        return ResponseData.Success(responseUserInfo);
    }

    @AuthHandle(value = {UserEnum.Admin})
    @PostMapping("/admin/del_user")
    public ResponseData<Object> deleteUser(HttpServletRequest request) {
        MultipartHttpServletRequest params = ((MultipartHttpServletRequest) request);
        String userName = params.getParameter("userName");
        loginService.deleteUser(userName);
        return ResponseData.Success("删除成功");
    }
}
