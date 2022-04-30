package com.example.enumparam.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Position {

    FRONTEND, BACKEND, DBA;

    @JsonCreator
    public static Position from(String s) {
        return Position.valueOf(s.toUpperCase());
    }
}
