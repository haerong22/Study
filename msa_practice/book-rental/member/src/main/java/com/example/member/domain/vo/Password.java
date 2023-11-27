package com.example.member.domain.vo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Password {

    private final String present;
    private final String past;

    public static Password create(String present, String past) {
        return new Password(present, past);
    }
}
