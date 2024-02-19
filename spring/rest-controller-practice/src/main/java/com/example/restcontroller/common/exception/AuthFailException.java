package com.example.restcontroller.common.exception;

public class AuthFailException extends RuntimeException {
    public AuthFailException(String message) {
        super(message);
    }
}
