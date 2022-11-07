package com.example.sns.fixture;

import com.example.sns.model.entity.UserEntity;

public class UserEntityFixture {

    public static UserEntity get(String username, String password, Integer userId) {
        UserEntity result = new UserEntity();
        result.setId(userId);
        result.setUsername(username);
        result.setPassword(password);
        return result;
    }
}
