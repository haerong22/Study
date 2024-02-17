package org.example.delivery.storeadmin.doamin.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.delivery.storeadmin.doamin.user.business.StoreUserBusiness;
import org.example.delivery.storeadmin.doamin.user.controller.model.StoreUserRegisterRequest;
import org.example.delivery.storeadmin.doamin.user.controller.model.StoreUserResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/open-api/store-user")
public class StoreUserOpenApiController {

    private final StoreUserBusiness storeUserBusiness;

    @PostMapping
    public StoreUserResponse register(@Valid @RequestBody StoreUserRegisterRequest request) {
        return storeUserBusiness.register(request);
    }
}
