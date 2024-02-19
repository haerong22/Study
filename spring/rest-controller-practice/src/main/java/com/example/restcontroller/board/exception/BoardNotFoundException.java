package com.example.restcontroller.board.exception;

public class BoardNotFoundException extends RuntimeException {
    public BoardNotFoundException(String s) {
        super(s);
    }
}
