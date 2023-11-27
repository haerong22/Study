package com.example.member.domain.vo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Email {

    private final String address;

    public static Email create(String email) {
        return new Email(email);
    }
}
