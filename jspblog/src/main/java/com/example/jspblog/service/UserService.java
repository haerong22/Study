package com.example.jspblog.service;

import com.example.jspblog.config.DB;
import com.example.jspblog.domain.user.User;
import com.example.jspblog.domain.user.UserDao;
import com.example.jspblog.domain.user.dto.JoinReqDto;
import com.example.jspblog.domain.user.dto.LoginReqDto;
import com.example.jspblog.domain.user.dto.UpdateReqDto;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UserService {
    private final UserDao userDao;

    public UserService() {
        userDao = new UserDao();
    }

    public int 회원가입(JoinReqDto dto) {
        int result = userDao.save(dto);
        return result;
    }

    public User 로그인(LoginReqDto dto) {
        return null;
    }

    public int 회원수정(UpdateReqDto dto) {
        return -1;
    }

    public int 아이디중복체크(String username) {
        return -1;
    }
}
