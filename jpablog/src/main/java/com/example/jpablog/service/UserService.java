package com.example.jpablog.service;

import com.example.jpablog.model.RoleType;
import com.example.jpablog.model.User;
import com.example.jpablog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // select 할 때 트랜잭션 시작, 서비스 종료시에 트랜잭션 종료 (정합성)
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Transactional
    public int 회원가입(User user) {
        String encPassword = encoder.encode(user.getPassword());
        user.setPassword(encPassword);
        user.setRole(RoleType.USER);
        userRepository.save(user);
        return 1;
    }

    @Transactional
    public void 회원수정(Long id, User user) {
        User persistence = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("회원 찾기 실패"));

        if (persistence.getOauth() == null || persistence.getOauth().equals("")) {
            String encPassword = encoder.encode(user.getPassword());
            persistence.setPassword(encPassword);
            persistence.setEmail(user.getEmail());
        }
    }

    public User 회원찾기(String username) {
        return userRepository.findByUsername(username).orElseGet(User::new);
    }

    /*// 기본 로그인
    public User 로그인(User user) {
        return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
    }*/
}
