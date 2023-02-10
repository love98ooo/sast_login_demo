package com.example.login_demo.annotation;

import com.example.login_demo.enums.UserEnum;

import java.lang.annotation.*;


@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthHandle {
    UserEnum[] value();
}
