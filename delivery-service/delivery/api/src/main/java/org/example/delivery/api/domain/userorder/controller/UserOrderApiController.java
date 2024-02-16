package org.example.delivery.api.domain.userorder.controller;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.delivery.api.common.annotation.UserSession;
import org.example.delivery.api.common.api.Api;
import org.example.delivery.api.domain.user.model.User;
import org.example.delivery.api.domain.userorder.business.UserOrderBusiness;
import org.example.delivery.api.domain.userorder.controller.model.UserOrderDetailResponse;
import org.example.delivery.api.domain.userorder.controller.model.UserOrderRequest;
import org.example.delivery.api.domain.userorder.controller.model.UserOrderResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-order")
@RequiredArgsConstructor
public class UserOrderApiController {

    private final UserOrderBusiness userOrderBusiness;


    @PostMapping
    public Api<UserOrderResponse> userOrder(
            @Valid @RequestBody UserOrderRequest userOrderRequest,
            @Parameter(hidden = true) @UserSession User user
    ) {
        return Api.ok(userOrderBusiness.userOrder(user, userOrderRequest));
    }

    @GetMapping("/current")
    public Api<List<UserOrderDetailResponse>> current(
            @Parameter(hidden = true) @UserSession User user
    ) {
        return Api.ok(userOrderBusiness.current(user));
    }

    @GetMapping("/history")
    public Api<List<UserOrderDetailResponse>> history(
            @Parameter(hidden = true) @UserSession User user
    ) {
        return Api.ok(userOrderBusiness.history(user));
    }

    @GetMapping("/{orderId}")
    public Api<UserOrderDetailResponse> read(
            @Parameter(hidden = true) @UserSession User user,
            @PathVariable Long orderId
    ) {
        return Api.ok(userOrderBusiness.read(user, orderId));
    }

}
