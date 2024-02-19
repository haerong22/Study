package com.example.restcontroller.board.exception;

public class BoardTypeNotFoundException extends RuntimeException {
    public BoardTypeNotFoundException(String message) {
        super(message);
    }
}
