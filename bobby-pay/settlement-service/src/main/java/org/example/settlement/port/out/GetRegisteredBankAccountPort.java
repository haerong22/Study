package org.example.settlement.port.out;

public interface GetRegisteredBankAccountPort {
    RegisteredBankAccountAggregateIdentifier getRegisteredBankAccount(String membershipId);

    // 타겟 계좌, 금액
    void requestFirmBanking(String bankName, String bankAccountNumber, int moneyAmount);
}