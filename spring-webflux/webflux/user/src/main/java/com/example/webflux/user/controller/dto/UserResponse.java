package com.example.webflux.user.controller.dto;

import lombok.Data;

@Data
public class UserResponse {
    private final String id;
    private final String name;
    private final int age;
}