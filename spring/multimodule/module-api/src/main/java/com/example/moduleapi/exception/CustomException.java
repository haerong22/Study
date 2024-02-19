package com.example.moduleapi.exception;

import com.example.modulecommon.enums.CodeEnum;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final String returnCode;
    private final String returnMessage;

    public CustomException(CodeEnum codeEnum) {
        super(codeEnum.getMessage());
        this.returnCode = codeEnum.getCode();
        this.returnMessage = codeEnum.getMessage();
    }
}
