package com.example.jpablog.service;

import com.example.jpablog.model.User;
import com.example.jpablog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // select 할 때 트랜잭션 시작, 서비스 종료시에 트랜잭션 종료 (정합성)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public int 회원가입(User user) {
        userRepository.save(user);
        return 1;
    }

    /*// 기본 로그인
    public User 로그인(User user) {
        return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
    }*/
}
