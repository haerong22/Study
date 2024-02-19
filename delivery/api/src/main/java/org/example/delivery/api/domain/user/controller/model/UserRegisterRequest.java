package org.example.delivery.api.domain.user.controller.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRegisterRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @NotBlank
    private String address;

    @NotBlank
    private String password;
}
