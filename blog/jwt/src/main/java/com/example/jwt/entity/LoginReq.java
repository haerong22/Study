package com.example.jwt.entity;

import lombok.Data;

@Data
public class LoginReq {

    private String username;
    private String password;
}
