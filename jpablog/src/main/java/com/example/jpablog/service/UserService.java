package com.example.jpablog.service;

import com.example.jpablog.model.User;
import com.example.jpablog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public int 회원가입(User user) {
        userRepository.save(user);
        return 1;
    }
}
