package com.example.userservice.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class RequestUser {

    @NotBlank(message = "Email can not be null")
    @Size(min = 2, message = "Email not less than 2 characters")
    private String email;

    @NotBlank(message = "Name can not be null")
    @Size(min = 2, message = "Name not less than 2 characters")
    private String name;

    @NotBlank(message = "Password can not be null")
    @Size(min = 8, message = "Password must be equal or grater than 8 characters")
    private String pwd;
}
