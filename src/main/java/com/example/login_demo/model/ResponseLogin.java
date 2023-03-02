package com.example.login_demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseLogin {
    private String userName;
    private String email;
    private Integer role;
    private Integer id;
    private String token;
}
