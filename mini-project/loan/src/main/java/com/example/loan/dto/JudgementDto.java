package com.example.loan.dto;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class JudgementDto implements Serializable {

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class Request {

        private Long applicationId;

        private String name;

        private BigDecimal approvalAmount;

    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class Response {

        private Long judgementId;

        private Long applicationId;

        private String name;

        private BigDecimal approvalAmount;

        private LocalDateTime createdAt;

        private LocalDateTime updatedAt;
    }
}
