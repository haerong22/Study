package com.example.springdatajpa.repository;

public interface NestedClosedProjections {

    String getUsername();
    TeamInfo getTeam();

    interface TeamInfo {
        String getName();
    }
}
