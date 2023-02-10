package com.example.login_demo.enums;

import com.example.login_demo.pojo.User;

public enum UserEnum {
    Admin("管理员", 1),
    User("普通用户", 0);


    private final String role;
    private final Integer code;

    UserEnum(String role, Integer code) {
        this.role = role;
        this.code = code;
    }

    public String getRole() {
        return role;
    }

    public Integer getCode() {
        return code;
    }

    public static Boolean checkUser(User user, UserEnum[] needRole) {
//      遍历注解的枚举
        for (UserEnum userEnum :
                needRole) {
//          判断权限是否符合要求
            if (userEnum.getCode().equals(user.getCode())) {
                return true;
            }
        }
        return false;
    }
}
