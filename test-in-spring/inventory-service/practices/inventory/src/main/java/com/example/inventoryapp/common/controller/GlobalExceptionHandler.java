package com.example.inventoryapp.common.controller;

import com.example.inventoryapp.inventory.controller.exception.CommonInventoryHttpException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.charset.StandardCharsets;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CommonInventoryHttpException.class)
    public ResponseEntity<ApiResponse<Void>> handelCommonInventoryHttpException(CommonInventoryHttpException e) {
        final ApiResponse<Void> body = ApiResponse.error(e.getErrorCodes());
        final MediaType contentType = new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8);
        final HttpStatus status = e.getStatus();

        return ResponseEntity.status(status)
                .contentType(contentType)
                .body(body);
    }
}
