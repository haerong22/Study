package com.example.restcontroller.user.entity;

public enum UserStatus {
    NONE, USING, STOP;

    int value;

    UserStatus() {
    }

    public int getValue() {
        return this.value;
    }
}
