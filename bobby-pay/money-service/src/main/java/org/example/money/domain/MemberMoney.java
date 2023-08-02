package org.example.money.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberMoney {

    private final String memberMoneyId;
    private final String membershipId;
    private final int balance;

    public static MemberMoney generateMemberMoney(
            MemberMoneyId memberMoneyId,
            MembershipId membershipId,
            Balance balance
    ) {
        return new MemberMoney(
                memberMoneyId.getMemberMoneyId(),
                membershipId.getMembershipId(),
                balance.getBalance()
        );
    }

    @Value
    public static class MemberMoneyId {

        String memberMoneyId;

        public MemberMoneyId(String value) {
            this.memberMoneyId = value;
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
    public static class Balance {

        int balance;

        public Balance(int value) {
            this.balance = value;
        }
    }
}
