package com.example.aop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonDto<T> {
    private T data;
    private int statusCode;

    public CommonDto(int statusCode) {
        this.statusCode = statusCode;
    }
}
