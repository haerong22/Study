package com.example.restcontroller.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPasswordUpdate {

    @NotBlank(message = "비밀번호를 입력하세요.")
    private String password;

    @Size(min = 4, max = 20, message = "비밀번호는 4-20 사이의 길이로 입력해 주세요.")
    @NotBlank(message = "비밀번호를 입력하세요.")
    private String newPassword;
}
