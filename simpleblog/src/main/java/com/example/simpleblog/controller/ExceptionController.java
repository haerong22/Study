package com.example.simpleblog.controller;

import com.example.simpleblog.exception.BizException;
import com.example.simpleblog.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse invalidRequestHandler(MethodArgumentNotValidException e) {

//        if (e.hasErrors()) {
            ErrorResponse response = ErrorResponse.builder()
                    .code("400")
                    .message("잘못된 요청입니다.")
                    .build();

            e.getFieldErrors().forEach(fieldError -> {
                response.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
            });
            return response;
//        }
    }

    @ExceptionHandler(BizException.class)
    public ResponseEntity<ErrorResponse> postNotFound(BizException e) {
        int statusCode = e.getStatusCode();

        ErrorResponse body = ErrorResponse.builder()
                .code(String.valueOf(statusCode))
                .message(e.getMessage())
                .validation(e.getValidation())
                .build();

        return ResponseEntity.status(statusCode)
                .body(body);
    }
}
