package com.example.sns.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    DUPLICATED_USER_NAME(HttpStatus.CONFLICT, "Username is duplicated."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not founded."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "Password is invalid."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "Token is invalid."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error.")

    ;

    private HttpStatus status;
    private String message;
}
