package org.example.money.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.util.Date;
import java.util.UUID;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MoneyChangingRequest {

    private final String moneyChargingRequestId;
    private final String targetMembershipId;
    private final int changingType; // 0: 증액, 1: 감액
    private final int changingMoneyAmount;
    private final int changingMoneyStatus; // 0: 요청, 1: 성공, 2: 실패
    private final String uuid;
    private final Date createdAt;

    public static MoneyChangingRequest generateMoneyChangingRequest(
            MoneyChargingRequestId moneyChargingRequestId,
            TargetMembershipId targetMembershipId,
            MoneyChangingType moneyChangingType,
            ChangingMoneyAmount changingMoneyAmount,
            MoneyChangingStatus moneyChangingStatus,
            Uuid uuid
    ) {
        return new MoneyChangingRequest(
                moneyChargingRequestId.getMoneyChargingRequestId(),
                targetMembershipId.getTargetMembershipId(),
                moneyChangingType.getChangingType(),
                changingMoneyAmount.getChangingMoneyAmount(),
                moneyChangingStatus.getChangingMoneyStatus(),
                uuid.getUuid(),
                new Date()
        );
    }

    @Value
    public static class MoneyChargingRequestId {

        String moneyChargingRequestId;

        public MoneyChargingRequestId(String value) {
            this.moneyChargingRequestId = value;
        }
    }

    @Value
    public static class TargetMembershipId {

        String targetMembershipId;

        public TargetMembershipId(String value) {
            this.targetMembershipId = value;
        }
    }

    @Value
    public static class MoneyChangingType {

        int changingType;

        public MoneyChangingType(int value) {
            this.changingType = value;
        }
    }

    @Value
    public static class ChangingMoneyAmount {

        int changingMoneyAmount;

        public ChangingMoneyAmount(int value) {
            this.changingMoneyAmount = value;
        }
    }

    @Value
    public static class MoneyChangingStatus {

        int changingMoneyStatus;

        public MoneyChangingStatus(int value) {
            this.changingMoneyStatus = value;
        }
    }

    @Value
    public static class Uuid {

        String uuid;

        public Uuid(UUID value) {
            this.uuid = value.toString();
        }
    }
}
