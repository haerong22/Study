package org.example.delivery.api.domain.storemenu.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.delivery.common.api.Api;
import org.example.delivery.api.domain.storemenu.business.StoreMenuBusiness;
import org.example.delivery.api.domain.storemenu.controller.model.StoreMenuRegisterRequest;
import org.example.delivery.api.domain.storemenu.controller.model.StoreMenuResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/open-api/store-menu")
public class StoreMenuOpenApiController {

    private final StoreMenuBusiness storeMenuBusiness;

    @PostMapping
    public Api<StoreMenuResponse> register(
            @Valid @RequestBody StoreMenuRegisterRequest request
    ) {
        return Api.ok(storeMenuBusiness.register(request));
    }
}
