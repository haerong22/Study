package com.example.aop.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UpdateReqDto {

    @NotBlank(message = "패스워드를 입력하세요")
    private String username;
    private String phone;
}
