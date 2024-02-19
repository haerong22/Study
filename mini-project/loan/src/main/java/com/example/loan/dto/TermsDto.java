package com.example.loan.dto;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TermsDto implements Serializable {

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Builder
    public static class Request {

        private String name;
        private String termsDetailUrl;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class Response {

        private Long termsId;
        private String name;
        private String termsDetailUrl;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

}
