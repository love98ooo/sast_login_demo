package com.example.login_demo.service.impl;

import com.example.login_demo.common.BizException;
import com.example.login_demo.common.ResponseEnum;
import com.example.login_demo.dao.LoginMapper;
import com.example.login_demo.pojo.User;
import com.example.login_demo.service.LoginService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private LoginMapper loginMapper;
    @Autowired
    private RedisTemplate redisTemplate = new RedisTemplate<String, User>();

    @Override
    public String findUser(String username, String userPassword) {
        User user = null;
        try {
            user = loginMapper.selectPassword(username);
        } catch (Exception e) {
            throw new BizException(ResponseEnum.NOT_USER);
        }
        if (!user.getPassword().equals(userPassword)) {
            throw new BizException(1001, "Password or Username error");
        }
        String tokenHead = "token_";
        String token = UUID.randomUUID() + "";
        user.setPassword(null);
        user.setUsername(username);
        setToken(user, tokenHead + token);
//            System.out.println("Login succeed");
        return token;
    }

    @Override
    public String findUsernameByToken(String token) {
        User user = new Gson().fromJson(Objects.requireNonNull(redisTemplate.opsForValue().get(token)).toString(), User.class);
        return user.getUsername();
    }

    @Override
    public String addUser(User user) {
        if (loginMapper.existUser(user.getUsername()) > 0) {
            throw new BizException(ResponseEnum.CREATE_USER_FAIL);
        } else {
            String[] roles = {
                    "User",
                    "Admin"};
            loginMapper.insertUser(new User(user.getUsername(), user.getPassword(), roles[user.getCode()], user.getCode(), user.getEmail()));
            return "成功";
        }
    }

    @Override
    public String changeUserPassword(String username, String newPassword) {
        loginMapper.updatePassword(username, newPassword);
        return "成功";
    }

    @Override
    public Boolean checkPassword(String username, String password) {
        User user = loginMapper.selectPassword(username);
        return user.getPassword().equals(password);
    }

    @Override
    public List<User> findAllUser() {
        return loginMapper.selectAllUser();
    }

    @Override
    public void deleteUser(String username) {
        loginMapper.deleteUser(username);
    }

    private void setToken(User user, String token) {
            redisTemplate.opsForValue().set(token, user);
            redisTemplate.expire(token, Duration.ofHours(1));
    }
}
