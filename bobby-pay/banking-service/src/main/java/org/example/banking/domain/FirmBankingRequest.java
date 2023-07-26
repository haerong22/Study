package org.example.banking.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.util.UUID;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FirmBankingRequest {

    private final String firmBankingRequestId;

    private final String fromBankName;
    private final String fromBankAccountNumber;

    private final String toBankName;
    private final String toBankAccountNumber;

    private final int moneyAmount;
    private final int firmBankingStatus; // 0: 요청, 1: 완료, 2: 실패
    private final UUID uuid;


    public static FirmBankingRequest generateFirmBankingRequest (
            FirmBankingRequestId firmBankingRequestId,
            FromBankName fromBankName,
            FromBankAccountNumber fromBankAccountNumber,
            ToBankName toBankName,
            ToBankAccountNumber toBankAccountNumber,
            MoneyAmount moneyAmount,
            FirmBankingStatus firmBankingStatus,
            UUID uuid
    ) {
        return new FirmBankingRequest(
                firmBankingRequestId.firmBankingRequestId,
                fromBankName.fromBankName,
                fromBankAccountNumber.fromBankAccountNumber,
                toBankName.toBankName,
                toBankAccountNumber.toBankAccountNumber,
                moneyAmount.moneyAmount,
                firmBankingStatus.firmBankingStatus,
                uuid
        );
    }

    @Value
    public static class FirmBankingRequestId {

        String firmBankingRequestId;

        public FirmBankingRequestId(String value) {
            this.firmBankingRequestId = value;
        }
    }

    @Value
    public static class FromBankName {

        String fromBankName;

        public FromBankName(String value) {
            this.fromBankName = value;
        }
    }

    @Value
    public static class FromBankAccountNumber {

        String fromBankAccountNumber;

        public FromBankAccountNumber(String value) {
            this.fromBankAccountNumber = value;
        }
    }

    @Value
    public static class ToBankName {

        String toBankName;

        public ToBankName(String value) {
            this.toBankName = value;
        }
    }

    @Value
    public static class ToBankAccountNumber {

        String toBankAccountNumber;

        public ToBankAccountNumber(String value) {
            this.toBankAccountNumber = value;
        }
    }

    @Value
    public static class MoneyAmount {

        int moneyAmount;

        public MoneyAmount(int value) {
            this.moneyAmount = value;
        }
    }

    @Value
    public static class FirmBankingStatus {

        int firmBankingStatus;

        public FirmBankingStatus(int value) {
            this.firmBankingStatus = value;
        }
    }
}
