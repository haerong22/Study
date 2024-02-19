package org.example.delivery.api.domain.user.controller.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
