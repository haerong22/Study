package com.example.restcontroller.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FindUserId {

    @NotBlank(message = "이름은 필수 항목 입니다.")
    private String userName;

    @NotBlank(message = "연락처는 필수 항목 입니다.")
    @Size(max = 20, message = "연락처는 최대 20자까지 입력해야 합니다.")
    private String phone;
}
