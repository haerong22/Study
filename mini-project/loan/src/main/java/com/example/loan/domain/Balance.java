package com.example.loan.domain;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Where(clause = "is_deleted=false")
public class Balance extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long balanceId;

    @Column(columnDefinition = "bigint NOT NULL COMMENT '신청 ID'")
    private Long applicationId;

    @Column(columnDefinition = "decimal(15, 2) NOT NULL COMMENT '잔여 대출 금액'")
    private BigDecimal balance;

}
