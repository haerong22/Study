package com.example.modulecommon.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CodeEnum {
    SUCCESS("0000", "SUCCESS"),
    UNKNOWN_ERROR("9999", "UNKNOWN_ERROR"),
    ;


    private final String code;
    private final String message;
}
