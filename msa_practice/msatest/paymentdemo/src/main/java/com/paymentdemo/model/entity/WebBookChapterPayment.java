package com.paymentdemo.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity
public class WebBookChapterPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long webBookChapterPaymentId;

    private Long webBookChapterId;

    private Long writerId;     // null,

    private Long readerId;

    private Integer amount;

    private Boolean isRefunded;

    private LocalDateTime refundedAt;

    private LocalDateTime createdAt;
}
