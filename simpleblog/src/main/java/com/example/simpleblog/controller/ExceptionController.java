package com.example.simpleblog.controller;

import com.example.simpleblog.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public ErrorResponse invalidRequestHandler(MethodArgumentNotValidException e) {

//        if (e.hasErrors()) {
            ErrorResponse response = new ErrorResponse("400", "잘못된 요청입니다.");

            e.getFieldErrors().forEach(fieldError -> {
                response.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
            });
            return response;
//        }
    }
}
