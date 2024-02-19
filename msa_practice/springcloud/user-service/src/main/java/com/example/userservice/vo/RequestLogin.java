package com.example.userservice.vo;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class RequestLogin {

    @NotBlank(message = "Email can`t be null")
    @Size(min = 2, message = "Email not be less than 2 characters")
    @Email
    private String email;
    @NotBlank(message = "Password can`t be null")
    @Size(min = 8, message = "Password must be equal or greater less than 8 characters")
    private String password;
}
