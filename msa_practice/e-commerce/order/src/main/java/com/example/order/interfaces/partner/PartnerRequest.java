package com.example.order.interfaces.partner;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class PartnerRequest {

    @Getter
    @Setter
    @ToString
    public static class Register {
        @NotEmpty(message = "partnerName 는 필수값입니다")
        private String partnerName;

        @NotEmpty(message = "businessNo 는 필수값입니다")
        private String businessNo;

        @Email(message = "email 형식에 맞아야 합니다")
        @NotEmpty(message = "email 는 필수값입니다")
        private String email;
    }
}
