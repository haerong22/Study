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
public class ReaderWebBookPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long readerWebBookPayment;

    private Long webBookChapterId;

    private Integer paymentAmount;

    private LocalDateTime createdAt;
}
