package org.example.delivery.storeadmin.doamin.storeuser.controller.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.example.delivery.db.storeuser.enums.StoreUserRole;

@Data
public class StoreUserRegisterRequest {

    @NotBlank
    private String storeName;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private StoreUserRole role;
}
