package com.example.restcontroller.notice.exception;

public class DuplicateNoticeException extends RuntimeException {

    public DuplicateNoticeException(String message) {
        super(message);
    }
}
