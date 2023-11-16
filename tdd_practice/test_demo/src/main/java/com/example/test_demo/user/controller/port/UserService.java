package com.example.test_demo.user.controller.port;

import com.example.test_demo.user.domain.User;
import com.example.test_demo.user.domain.UserCreate;
import com.example.test_demo.user.domain.UserUpdate;

public interface UserService {

    User getByEmail(String email);
    User getById(long id);
    User create(UserCreate userCreate);
    User update(long id, UserUpdate userUpdate);
    void login(long id);
    void verifyEmail(long id, String certificationCode);
}
