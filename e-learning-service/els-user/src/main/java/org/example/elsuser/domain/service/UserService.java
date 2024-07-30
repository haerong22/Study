package org.example.elsuser.domain.service;

import lombok.RequiredArgsConstructor;
import org.example.elsuser.domain.entity.User;
import org.example.elsuser.domain.entity.UserLoginHistory;
import org.example.elsuser.domain.exception.UserNotFoundException;
import org.example.elsuser.domain.repository.UserLoginHistoryRepository;
import org.example.elsuser.domain.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserLoginHistoryRepository userLoginHistoryRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User createUser(String name, String email, String password) {
        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPasswordHash(passwordEncoder.encode(password));
        return userRepository.save(newUser);
    }

    public User getUserById(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
    }

    public User getUserByEmail(String userId) {
        return userRepository.findByEmail(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
    }

    @Transactional
    public User updateUser(Integer userId, String name, String email) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
        user.setName(name);
        user.setEmail(email);
        return userRepository.save(user);
    }

    public List<UserLoginHistory> getUserLoginHistories(Integer userId) {
        return userRepository.findById(userId)
                .map(User::getLoginHistories)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
    }

    public void changePassword(Integer userId, String oldPassword, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (passwordEncoder.matches(oldPassword, user.getPasswordHash())) {
            user.setPasswordHash(passwordEncoder.encode(newPassword));
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("Invalid old password");
        }
    }

    @Transactional
    public void logUserLogin(User user, String ipAddress) {
        UserLoginHistory loginHistory = new UserLoginHistory();
        loginHistory.setUser(user);
        loginHistory.setLoginTime(LocalDateTime.now());
        loginHistory.setIpAddress(ipAddress);
        userLoginHistoryRepository.save(loginHistory);
    }
}
