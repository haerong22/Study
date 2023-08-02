package org.example.money.adapter.out.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "money_changing_request")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoneyChangingRequestJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long moneyChangingRequestId;
    private Long targetMembershipId;
    private int moneyChangingType;
    private int moneyAmount;
    private int changingMoneyStatus; // 0: 요청, 1: 성공, 2: 실패

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    private UUID uuid;

    public MoneyChangingRequestJpaEntity(Long targetMembershipId, int moneyChangingType, int moneyAmount, int changingMoneyStatus, Timestamp timestamp, UUID uuid) {
        this.targetMembershipId = targetMembershipId;
        this.moneyChangingType = moneyChangingType;
        this.moneyAmount = moneyAmount;
        this.changingMoneyStatus = changingMoneyStatus;
        this.timestamp = timestamp;
        this.uuid = uuid;
    }
}
