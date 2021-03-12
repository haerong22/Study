package com.example.restcontroller.notice.exception;

public class AlreadyDeletedException extends RuntimeException {

    public AlreadyDeletedException(String message) {
        super(message);
    }
}
