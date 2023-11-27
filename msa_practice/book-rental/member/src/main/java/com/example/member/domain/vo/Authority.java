package com.example.member.domain.vo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Authority {

    private final UserRole roleName;

    public static Authority create(UserRole role) {
        return new Authority(role);
    }
}
