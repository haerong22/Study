package org.example.delivery.api.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.example.delivery.api.common.error.CommonErrorCode;
import org.example.delivery.api.common.exception.ApiException;
import org.example.delivery.db.user.UserEntity;
import org.example.delivery.db.user.UserRepository;
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
}
