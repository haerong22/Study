package com.example.webflux.r2dbc.controller.dto;

import com.example.webflux.r2dbc.common.User;
import lombok.Data;

import java.util.Optional;

@Data
public class UserResponse {
    private final String id;
    private final String name;
    private final int age;
    private final Long followCount;
    private final Optional<ProfileImageResponse> image;

    public static UserResponse of(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getAge(),
                user.getFollowCount(),
                user.getProfileImage().map(image ->
                        new ProfileImageResponse(
                                image.getId(),
                                image.getName(),
                                image.getUrl()))
        );
    }
}