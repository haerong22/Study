package com.example.aop.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class JoinReqDto {

    @NotBlank(message = "유저네임을 입력하세요.")
    @Size(max = 20, message = "길이를 초과하였습니다.")
    private String username;

    @NotBlank(message = "비밀번호가 없습니다.")
    private String password;
    private String phone;
}
