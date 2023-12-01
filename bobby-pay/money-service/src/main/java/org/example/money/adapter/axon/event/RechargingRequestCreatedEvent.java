package org.example.money.adapter.axon.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 충전 동작이 생성되었다는 이벤트
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RechargingRequestCreatedEvent {

    private String rechargingRequestId;

    private String membershipId;

    private int amount;

    private String registeredBankAccountAggregateIdentifier;

    private String bankName;

    private String bankAccountNumber;
}
