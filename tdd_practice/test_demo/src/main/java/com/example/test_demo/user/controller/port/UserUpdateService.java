package com.example.test_demo.user.controller.port;

import com.example.test_demo.user.domain.User;
import com.example.test_demo.user.domain.UserUpdate;

public interface UserUpdateService {

    User update(long id, UserUpdate userUpdate);
}
