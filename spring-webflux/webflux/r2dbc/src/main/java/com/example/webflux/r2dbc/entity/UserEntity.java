package com.example.webflux.r2dbc.entity;

import lombok.Data;

@Data
public class UserEntity {
    private final String id;
    private final String name;
    private final int age;
    private final String profileImageId;
    private final String password;
}