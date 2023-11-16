package com.example.test_demo.user.controller.port;

import com.example.test_demo.user.domain.User;

public interface UserReadService {

    User getByEmail(String email);
    User getById(long id);
}
