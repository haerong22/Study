package com.example.test_demo.user.service;

import com.example.test_demo.common.domain.exception.ResourceNotFoundException;
import com.example.test_demo.common.service.port.ClockHolder;
import com.example.test_demo.common.service.port.UuidHolder;
import com.example.test_demo.user.controller.port.*;
import com.example.test_demo.user.domain.User;
import com.example.test_demo.user.domain.UserCreate;
import com.example.test_demo.user.domain.UserStatus;
import com.example.test_demo.user.domain.UserUpdate;
import com.example.test_demo.user.service.port.UserRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Builder
@RequiredArgsConstructor
public class UserServiceImpl implements UserCreateService, UserReadService, UserUpdateService, AuthenticationService {

    private final UserRepository userRepository;
    private final CertificationService certificationService;
    private final UuidHolder uuidHolder;
    private final ClockHolder clockHolder;

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmailAndStatus(email, UserStatus.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("Users", email));
    }

    @Override
    public User getById(long id) {
        return userRepository.findByIdAndStatus(id, UserStatus.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("Users", id));
    }

    @Override
    @Transactional
    public User create(UserCreate userCreate) {
        User user = User.from(userCreate, uuidHolder);
        user = userRepository.save(user);
        certificationService.send(userCreate.getEmail(), user.getId(), user.getCertificationCode());
        return user;
    }

    @Override
    @Transactional
    public User update(long id, UserUpdate userUpdate) {
        User user = getById(id);
        user = user.update(userUpdate);
        user = userRepository.save(user);
        return user;
    }

    @Override
    @Transactional
    public void login(long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Users", id));
        userRepository.save(user.login(clockHolder));
    }

    @Override
    @Transactional
    public void verifyEmail(long id, String certificationCode) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Users", id));
        userRepository.save(user.certificate(certificationCode, clockHolder));
    }

}