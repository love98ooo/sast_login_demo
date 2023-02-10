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
    private String userName;
    private String password;
    private String role;
    private int code;
    private String email;
    private String token;

    public User(String userName, String password, String role, String email, String token) {
        this.userName = userName;
        this.password = password;
        this.role = role;
        this.email = email;
        this.token = token;
    }

    public User(String userName, String password, String role, int code, String email) {
        this.userName = userName;
        this.password = password;
        this.role = role;
        this.code = code;
        this.email = email;
    }

    public User(int id, String userName, String role, String email, String token) {
        this.id = id;
        this.userName = userName;
        this.role = role;
        this.email = email;
        this.token = token;
    }

    public User(String userName, String password, String email) {
        this.userName = userName;
        this.password = password;
        this.email = email;
    }

    public User(int id, String userName, String password, String role, int code, String email) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.role = role;
        this.code = code;
        this.email = email;
    }
}
