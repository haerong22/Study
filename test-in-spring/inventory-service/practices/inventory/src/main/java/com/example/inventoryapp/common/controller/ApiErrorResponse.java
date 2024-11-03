package com.example.inventoryapp.common.controller;

import org.jetbrains.annotations.NotNull;

public record ApiErrorResponse(
        @NotNull Long code,
        @NotNull String localMessage
) {
}
