package com.example.simpleblog.exception;

import lombok.Getter;

@Getter
public class InvalidRequest extends BizException {

    private static final String MESSAGE = "잘못된 요청입니다.";

    private String fieldName;
    private String message;

    public InvalidRequest() {
        super(MESSAGE);
        addValidation(fieldName, message);
    }

    public InvalidRequest(String fieldName, String message) {
        super(MESSAGE);
        this.fieldName = fieldName;
        this.message = message;
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
