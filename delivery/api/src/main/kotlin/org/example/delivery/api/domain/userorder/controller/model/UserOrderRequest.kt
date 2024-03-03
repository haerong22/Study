package org.example.delivery.api.domain.userorder.controller.model

import jakarta.validation.constraints.NotNull

data class UserOrderRequest (
    var storeId: @NotNull Long? = null,
    var storeMenuIdList: @NotNull MutableList<Long>? = null,
)
