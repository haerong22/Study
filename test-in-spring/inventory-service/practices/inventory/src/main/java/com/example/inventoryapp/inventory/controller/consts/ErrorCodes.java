package com.example.inventoryapp.inventory.controller.consts;

import org.jetbrains.annotations.NotNull;

public enum ErrorCodes {
    ITEM_NOT_FOUND(1000L, "자산이 존재하지 않습니다.");

    public final @NotNull Long code;
    public final @NotNull String message;

    ErrorCodes(@NotNull Long code, @NotNull String message) {
        this.code = code;
        this.message = message;
    }
}
