package com.monolithicdemo.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table
@Entity
public class WriterWebBookPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long writerWebBookPayment;

    private Long webBookChapterId;

    private Long readerWebBookPaymentId;

    private Integer paymentAmount;

    private boolean isWithdraw; // default = false

    private LocalDateTime createdAt;
}
