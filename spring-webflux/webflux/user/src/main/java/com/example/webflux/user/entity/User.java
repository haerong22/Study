package com.example.webflux.user.entity;

import lombok.Data;

@Data
public class User {
    private final String id;
    private final String name;
    private final int age;
}