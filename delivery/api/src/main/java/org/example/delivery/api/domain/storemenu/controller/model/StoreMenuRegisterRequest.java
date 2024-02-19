package org.example.delivery.api.domain.storemenu.controller.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class StoreMenuRegisterRequest {

    @NotNull
    private Long storeId;

    @NotBlank
    private String name;

    @NotNull
    private BigDecimal amount;

    @NotBlank
    private String thumbnailUrl;
}
