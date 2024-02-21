package org.example.delivery.api.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.example.delivery.common.error.CommonErrorCode;
import org.example.delivery.common.error.UserErrorCode;
import org.example.delivery.common.exception.ApiException;
import org.example.delivery.db.user.UserEntity;
import org.example.delivery.db.user.UserRepository;
import org.example.delivery.db.user.enums.UserStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserEntity register(UserEntity userEntity) {
        return Optional.ofNullable(userEntity)
                .map(it -> userRepository.save(it.register()))
                .orElseThrow(() -> new ApiException(CommonErrorCode.NULL_POINT, "UserEntity"));
    }

    public UserEntity login(String email, String password) {
        return getUserWithThrow(email, password);
    }

    public UserEntity getUserWithThrow(String email, String password) {
        return userRepository.findFirstByEmailAndPasswordAndStatusOrderByIdDesc(
                email,
                password,
                UserStatus.REGISTERED
        ).orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));
    }

    public UserEntity getUserWithThrow(Long userId) {
        return userRepository.findFirstByIdAndStatusOrderByIdDesc(
                userId,
                UserStatus.REGISTERED
        ).orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));
    }
}
