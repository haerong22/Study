package com.example.webflux.r2dbc.entity;

import lombok.Data;

@Data
public class User {
    private final String id;
    private final String name;
    private final int age;
    private final Image profileImage;
}