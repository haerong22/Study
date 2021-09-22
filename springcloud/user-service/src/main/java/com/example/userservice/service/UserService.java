package com.example.userservice.service;

import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    void createUser(UserDto userDto);
    UserDto getUserByUserId(String userId);
    List<UserEntity> gerUserByAll();
}
