package com.example.login_demo.aspect;

import com.example.login_demo.annotation.AuthHandle;
import com.example.login_demo.common.BizException;
import com.example.login_demo.common.ResponseEnum;
import lombok.extern.slf4j.Slf4j;
import com.example.login_demo.enums.UserEnum;
import com.example.login_demo.pojo.User;
import com.google.gson.Gson;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.Objects;

@Slf4j
@Aspect
@Component
public class AuthAspect {
    private final HttpServletRequest request;
    public AuthAspect(HttpServletRequest request) {
        this.request = request;
    }
    @Resource
    private RedisTemplate redisTemplate;
    @Pointcut("@annotation(com.example.login_demo.annotation.AuthHandle)")
    public void start() {
    }

    @Before("start()&&@annotation(authHandle)")
    public Object auth(JoinPoint joinPoint, AuthHandle authHandle) {
        UserEnum[] authEnum = authHandle.value();
//        System.out.println("In AOP");
        String token = "token_" + request.getHeader("Token");
        User user = null;
        try {
            user = new Gson().fromJson(Objects.requireNonNull(redisTemplate.opsForValue().get(token)).toString(),
                    User.class);
        } catch (RuntimeException e) {
            log.error(e.toString());
            throw new BizException(ResponseEnum.USER_NOT_LOGIN);
        }
        if (!UserEnum.checkUser(user, authEnum)) {
            log.error(user.getUserName() +
                      " is trying to access to an illegal function," +
                      " but Role Limited");
            throw new BizException(ResponseEnum.USER_ROLE_LIMITED);
        } else {
            redisTemplate.expire(token, Duration.ofHours(1));
            return joinPoint;
        }
    }
}
