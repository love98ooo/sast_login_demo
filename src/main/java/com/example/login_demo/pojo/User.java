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
    private int role;
    private String email;
    private String token;

    public User(String userName, String password, int role, String email, String token) {
        this.userName = userName;
        this.role = role;
        this.password = password;
        this.email = email;
        this.token = token;
    }

    public User(String userName, String password, int role, String email) {
        this.userName = userName;
        this.password = password;
        this.role = role;
        this.email = email;
    }

    public User(int id, String userName, int role, String email, String token) {
        this.id = id;
        this.role = role;
        this.userName = userName;
        this.email = email;
        this.token = token;
    }

    public User(String userName, String password, String email) {
        this.userName = userName;
        this.password = password;
        this.email = email;
    }

    public User(int id, String userName, String password, int role, String email) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.role = role;
        this.email = email;
    }
}
