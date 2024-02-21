package org.example.delivery.api.domain.store.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.delivery.common.api.Api;
import org.example.delivery.api.domain.store.business.StoreBusiness;
import org.example.delivery.api.domain.store.controller.model.StoreRegisterRequest;
import org.example.delivery.api.domain.store.controller.model.StoreResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/open-api/store")
public class StoreOpenApiController {

    private final StoreBusiness storeBusiness;

    @PostMapping
    public Api<StoreResponse> register(
            @Valid @RequestBody StoreRegisterRequest request
    ) {
        return Api.ok(storeBusiness.register(request));
    }
}

