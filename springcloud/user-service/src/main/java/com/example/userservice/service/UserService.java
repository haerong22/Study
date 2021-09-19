package com.example.userservice.service;

import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.UserEntity;

import java.util.List;

public interface UserService {

    void createUser(UserDto userDto);
    UserDto getUserByUserId(String userId);
    List<UserEntity> gerUserByAll();
}
