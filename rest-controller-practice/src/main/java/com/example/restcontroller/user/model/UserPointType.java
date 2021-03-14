package com.example.restcontroller.user.model;

public enum UserPointType {

    NONE(0),
    USER_REGISTER(100),
    ADD_POST(200),
    ADD_COMMENT(150),
    ADD_LIKE(50);

    int value;

    UserPointType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
