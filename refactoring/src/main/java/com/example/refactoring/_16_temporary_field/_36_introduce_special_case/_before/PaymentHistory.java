package com.example.refactoring._16_temporary_field._36_introduce_special_case._before;

public class PaymentHistory {

    private int weeksDelinquentInLastYear;

    public PaymentHistory(int weeksDelinquentInLastYear) {
        this.weeksDelinquentInLastYear = weeksDelinquentInLastYear;
    }

    public int getWeeksDelinquentInLastYear() {
        return this.weeksDelinquentInLastYear;
    }
}
