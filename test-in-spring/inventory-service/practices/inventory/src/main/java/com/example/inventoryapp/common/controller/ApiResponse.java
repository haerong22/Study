package com.example.inventoryapp.common.controller;

import com.example.inventoryapp.inventory.controller.consts.ErrorCodes;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T> (
        @Nullable T data,
        @Nullable ApiErrorResponse error
) {

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(data, null);
    }

    public static <T> ApiResponse<T> error(@NotNull ErrorCodes errorCodes) {
        return new ApiResponse<>(
                null,
                new ApiErrorResponse(errorCodes.code, errorCodes.message)
        );
    }
}
