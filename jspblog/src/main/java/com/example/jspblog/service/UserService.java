package com.example.jspblog.service;

import com.example.jspblog.domain.user.User;
import com.example.jspblog.domain.user.UserDao;
import com.example.jspblog.domain.user.dto.JoinReqDto;
import com.example.jspblog.domain.user.dto.LoginReqDto;
import com.example.jspblog.domain.user.dto.UpdateReqDto;

public class UserService {
    private final UserDao userDao;

    public UserService() {
        userDao = new UserDao();
    }

    public int 회원가입(JoinReqDto dto) {
        return userDao.save(dto);
    }

    public User 로그인(LoginReqDto dto) {
        return null;
    }

    public int 회원수정(UpdateReqDto dto) {
        return -1;
    }

    public int 유저네임중복체크(String username) {
        return userDao.findByUsername(username);
    }
}
