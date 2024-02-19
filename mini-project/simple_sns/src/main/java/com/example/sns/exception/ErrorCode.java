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
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "Post not founded."),
    INVALID_PERMISSION(HttpStatus.UNAUTHORIZED, "Permission is invalid."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error."),
    ALREADY_LIKED(HttpStatus.CONFLICT, "User already liked the post."),
    ALARM_CONNECT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Connecting alarm occurs error."),

    ;

    private HttpStatus status;
    private String message;
}
