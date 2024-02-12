package org.example.delivery.api.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.example.delivery.api.common.api.Api;
import org.example.delivery.api.common.error.CommonErrorCode;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@Order(Integer.MAX_VALUE)
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Api<?>> exception(Exception ex) {
        log.error("", ex);

        return ResponseEntity
                .status(500)
                .body(Api.error(CommonErrorCode.SERVER_ERROR));
    }
}
