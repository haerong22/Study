package org.example.delivery.api.domain.user.converter;

import lombok.RequiredArgsConstructor;
import org.example.delivery.common.annotation.Converter;
import org.example.delivery.common.error.CommonErrorCode;
import org.example.delivery.common.exception.ApiException;
import org.example.delivery.api.domain.user.controller.model.UserRegisterRequest;
import org.example.delivery.api.domain.user.controller.model.UserResponse;
import org.example.delivery.db.user.UserEntity;

import java.util.Optional;

@Converter
@RequiredArgsConstructor
public class UserConverter {

    public UserEntity toEntity(UserRegisterRequest request) {
        return Optional.ofNullable(request)
                .map(it -> {
                    return UserEntity.builder()
                            .name(request.getName())
                            .email(request.getEmail())
                            .password(request.getPassword())
                            .address(request.getAddress())
                            .build();
                })
                .orElseThrow(() -> new ApiException(CommonErrorCode.NULL_POINT, "UserRegisterRequest"));
    }

    public UserResponse toResponse(UserEntity userEntity) {
        return Optional.ofNullable(userEntity)
                .map(it -> {
                    return UserResponse.builder()
                            .id(it.getId())
                            .name(it.getName())
                            .status(it.getStatus())
                            .email(it.getEmail())
                            .address(it.getAddress())
                            .registeredAt(it.getRegisteredAt())
                            .unregisteredAt(it.getUnregisteredAt())
                            .lastLoginAt(it.getLastLoginAt())
                            .build();
                })
                .orElseThrow(() -> new ApiException(CommonErrorCode.NULL_POINT, "UserEntity"));
    }
}
