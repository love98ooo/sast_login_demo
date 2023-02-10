package com.example.login_demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int id;
    private String username;
    private String password;
    private String role;
    private int code;
    private String email;

    public User(String username, String password, String role, int code, String email) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.code = code;
        this.email = email;
    }
}
