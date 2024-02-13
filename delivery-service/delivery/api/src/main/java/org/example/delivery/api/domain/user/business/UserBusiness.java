package org.example.delivery.api.domain.user.business;

import lombok.RequiredArgsConstructor;
import org.example.delivery.api.common.annotation.Business;
import org.example.delivery.api.domain.user.controller.model.UserRegisterRequest;
import org.example.delivery.api.domain.user.controller.model.UserResponse;
import org.example.delivery.api.domain.user.converter.UserConverter;
import org.example.delivery.api.domain.user.service.UserService;
import org.example.delivery.db.user.UserEntity;

@Business
@RequiredArgsConstructor
public class UserBusiness {

    private final UserService userService;
    private final UserConverter userConverter;

    public UserResponse register(UserRegisterRequest request) {
        UserEntity entity = userConverter.toEntity(request);
        UserEntity registered = userService.register(entity);
        return userConverter.toResponse(registered);
    }
}
