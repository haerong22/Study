package org.example.delivery.api.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.example.delivery.api.common.api.Api;
import org.example.delivery.api.common.error.ErrorCode;
import org.example.delivery.api.common.exception.ApiException;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@Order(Integer.MIN_VALUE)
public class ApiExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Api<?>> apiException(ApiException apiException) {
        log.error("", apiException);

        ErrorCode errorCode = apiException.getErrorCode();

        return ResponseEntity
                .status(errorCode.getHttpStatusCode())
                .body(Api.error(errorCode, apiException.getErrorDescription()));
    }
}
