package org.example.banking.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RegisteredBankAccount {

    private final String registeredBankAccountId;
    private final String membershipId;
    private final String bankName;
    private final String bankAccountNumber;
    private final boolean linkedStatusIsValid;


    public static RegisteredBankAccount generateRegisteredBankAccount(
            RegisteredBankAccountId registeredBankAccountId,
            MembershipId membershipId,
            BankName bankName,
            BankAccountNumber bankAccountNumber,
            LinkedStatusIsValid linkedStatusIsValid
    ) {
        return new RegisteredBankAccount(
                registeredBankAccountId.registeredBankAccountId,
                membershipId.membershipId,
                bankName.bankName,
                bankAccountNumber.bankAccountNumber,
                linkedStatusIsValid.linkedStatusIsValid
        );
    }

    @Value
    public static class RegisteredBankAccountId {

        String registeredBankAccountId;

        public RegisteredBankAccountId(String value) {
            this.registeredBankAccountId = value;
        }
    }

    @Value
    public static class MembershipId {

        String membershipId;

        public MembershipId(String value) {
            this.membershipId = value;
        }
    }

    @Value
    public static class BankName {

        String bankName;

        public BankName(String value) {
            this.bankName = value;
        }
    }

    @Value
    public static class BankAccountNumber {

        String bankAccountNumber;

        public BankAccountNumber(String value) {
            this.bankAccountNumber = value;
        }
    }

    @Value
    public static class LinkedStatusIsValid {

        boolean linkedStatusIsValid;

        public LinkedStatusIsValid(boolean value) {
            this.linkedStatusIsValid = value;
        }
    }
}
