package com.example.test_demo.user.controller.port;

import com.example.test_demo.user.domain.User;
import com.example.test_demo.user.domain.UserCreate;

public interface UserCreateService {

    User create(UserCreate userCreate);
}
