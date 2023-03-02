package com.example.login_demo.model;

import com.example.login_demo.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseUserInfo {
    private String userName;
    private String email;

    public ResponseUserInfo(User user) {
        this.userName = user.getUserName();
        this.email = user.getEmail();
    }
}
