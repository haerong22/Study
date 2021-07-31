package com.userdemo.model.entity;

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
public class ReaderWebBookChapter {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long readerWebBookChapterId;

    private Long webBookChapterId;

    private Long readerId;

    private Long webBookChapterPaymentId;

    private Integer paymentAmount;

    private LocalDateTime createdAt;


}
