package com.example.inventoryapp.inventory.controller.consts;

import org.jetbrains.annotations.NotNull;

public enum ErrorCodes {
    ITEM_NOT_FOUND(1000L, "자산이 존재하지 않습니다."),
    INSUFFICIENT_STOCK(1001L, "재고가 부족합니다."),
    INVALID_DECREASE_QUANTITY(1002L, "차감 수량이 유효하지 않습니다."),
    INVALID_STOCK(1003L, "재고가 유효하지 않습니다."),
    ;

    public final @NotNull Long code;
    public final @NotNull String message;

    ErrorCodes(@NotNull Long code, @NotNull String message) {
        this.code = code;
        this.message = message;
    }
}
