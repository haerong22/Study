package com.example.webflux.dto;

import lombok.Data;

@Data
public class UserUpdateRequest {

    private String name;
    private String email;
}
