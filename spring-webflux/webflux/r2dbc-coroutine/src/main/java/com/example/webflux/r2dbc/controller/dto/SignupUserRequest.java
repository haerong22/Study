package com.example.webflux.r2dbc.controller.dto;

import lombok.Data;

@Data
public class SignupUserRequest {
    private String name;
    private Integer age;
    private String password;
    private String profileImageId;
}