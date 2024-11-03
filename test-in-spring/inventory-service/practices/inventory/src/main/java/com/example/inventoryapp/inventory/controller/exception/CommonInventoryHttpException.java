package com.example.inventoryapp.inventory.controller.exception;

import com.example.inventoryapp.inventory.controller.consts.ErrorCodes;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;

public class CommonInventoryHttpException extends RuntimeException {
    private @NotNull final ErrorCodes errorCodes;
    private @NotNull final HttpStatus status;

    public CommonInventoryHttpException(@NotNull ErrorCodes errorCodes, @NotNull HttpStatus status) {
        this.errorCodes = errorCodes;
        this.status = status;
    }

    public @NotNull ErrorCodes getErrorCodes() {
        return errorCodes;
    }

    public @NotNull HttpStatus getStatus() {
        return status;
    }
}
