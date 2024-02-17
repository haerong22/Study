package org.example.delivery.storeadmin.doamin.user.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.example.delivery.storeadmin.doamin.authorization.model.UserSession;
import org.example.delivery.storeadmin.doamin.user.controller.model.StoreUserResponse;
import org.example.delivery.storeadmin.doamin.user.converter.StoreUserConverter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/store-user")
public class StoreUserApiController {

    private final StoreUserConverter storeUserConverter;

    @GetMapping("/me")
    public StoreUserResponse me(
            @Parameter(hidden = true) @AuthenticationPrincipal UserSession userSession
    ) {
        return storeUserConverter.toResponse(userSession);
    }
}
