package com.example.restcontroller.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInput {

    @NotBlank(message = "이메일은 필수 항목 입니다.")
    @Email(message = "이메일 형식에 맞게 입력해 주세요.")
    private String email;

    @NotBlank(message = "이름은 필수 항목 입니다.")
    private String userName;

    @NotBlank(message = "비밀번호는 필수 항목 입니다.")
    @Size(min = 4, message = "비밀번호는 4자 이상 입력해야 합니다.")
    private String password;

    @NotBlank(message = "연락처는 필수 항목 입니다.")
    @Size(max = 20, message = "연락처는 최대 20자까지 입력해야 합니다.")
    private String phone;
}
