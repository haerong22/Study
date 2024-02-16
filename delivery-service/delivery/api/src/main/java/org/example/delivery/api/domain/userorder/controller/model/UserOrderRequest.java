package org.example.delivery.api.domain.userorder.controller.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class UserOrderRequest {

    @NotNull
    private List<Long> storeMenuIdList;
}
