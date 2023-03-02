package com.example.login_demo.service.impl;

import com.example.login_demo.common.BizException;
import com.example.login_demo.common.ResponseEnum;
import com.example.login_demo.dao.LoginMapper;
import com.example.login_demo.model.ResponseLogin;
import com.example.login_demo.model.ResponseUserInfo;
import com.example.login_demo.pojo.User;
import com.example.login_demo.service.LoginService;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {
    final String tokenHead = "token_";
    @Resource
    private LoginMapper loginMapper;
    @Resource
    private RedisTemplate redisTemplate = new RedisTemplate<String, User>();

    @Override
    public ResponseLogin findUser(String userName, String userPassword) {
        User user = new User();
        try {
            user = loginMapper.selectUser(userName);
        } catch (Exception e) {
            throw new BizException(ResponseEnum.NOT_USER);
        }
        if (!user.getPassword().equals(userPassword)) {
            throw new BizException(1001, "Password or Username error");
        }
        String token = UUID.randomUUID() + "";
        ResponseLogin responseLogin = new ResponseLogin(userName, user.getEmail(), user.getRole(), user.getId(), token);
        setToken(user, tokenHead + token);
        return responseLogin;
    }

    @Override
    public User findUserInfo(String userName) {
        try {
            User user = loginMapper.selectUser(userName);
            user.setPassword(null);
            return user;
        } catch (Exception e) {
            throw new BizException(ResponseEnum.NOT_USER);
        }
    }

    @Override
    public String findUsernameByToken(String token) {
        return findUserByToken(token).getUserName();
    }

    private User findUserByToken(String token) {
        try {
            return new Gson().fromJson(Objects.requireNonNull(redisTemplate.opsForValue().get(tokenHead + token)).toString(), User.class);
        } catch (JsonSyntaxException e) {
            throw new BizException(ResponseEnum.USER_NOT_LOGIN);
        }
    }


    @Override
    public String addUser(User user) {
        if (loginMapper.existUser(user.getUserName()) > 0) {
            throw new BizException(ResponseEnum.CREATE_USER_FAIL);
        }
        int roleDefault = 0;
        loginMapper.insertUser(new User(user.getUserName(), user.getPassword(), roleDefault, user.getEmail()));
        return "成功";
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public String changeUserInfo(User user, String token) {
        int id = findUserByToken(token).getId();

        updateUserName(id, user.getUserName());
        updatePassword(id, user.getPassword());
        updateEmail(id, user.getEmail());

        return "成功";
    }

/*    @Override
    public Boolean checkPassword(String userName, String password) {
        User user = loginMapper.selectUser(userName);
        return user.getPassword().equals(password);
    }*/
    @Override
    public List<ResponseUserInfo> findAllUser() {
        List<User> users = loginMapper.selectAllUser();
        List<ResponseUserInfo> responseUserInfoList = new ArrayList<>();
        for (User user :
                users) {
            responseUserInfoList.add(new ResponseUserInfo(user));
        }
        return responseUserInfoList;
    }

    @Override
    public void deleteUser(String userName) {
        loginMapper.deleteUser(userName);
    }

    private void setToken(User user, String token) {
        redisTemplate.opsForValue().set(token, user);
        redisTemplate.expire(token, Duration.ofHours(1));
    }

    private void updateUserName(Integer id, String userName) {
        if (!userName.equals("")) {
            if (loginMapper.existUser(userName) > 0) {
                throw new BizException(ResponseEnum.CREATE_USER_FAIL);
            }
            loginMapper.updateUserName(id, userName);
        }
    }

    private void updatePassword(Integer id, String password) {
        final String nullInSha256 = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";
        if (!(password.equals("") || password.equals(nullInSha256))) {
            loginMapper.updatePassword(id, password);
        }
    }

    private void updateEmail(Integer id, String email) {
        if (!email.equals("")) {
            loginMapper.updateEmail(id, email);
        }
    }
}
