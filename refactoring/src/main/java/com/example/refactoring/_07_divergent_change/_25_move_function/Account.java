package com.example.refactoring._07_divergent_change._25_move_function;

public class Account {

    private int daysOverdrawn;

    private AccountType type;

    public Account(int daysOverdrawn, AccountType type) {
        this.daysOverdrawn = daysOverdrawn;
        this.type = type;
    }

    public double getBankCharge() {
        double result = 4.5;
        if (this.daysOverdrawn() > 0) {
            result += this.type.overdraftCharge(this.daysOverdrawn());
        }
        return result;
    }

    private int daysOverdrawn() {
        return this.daysOverdrawn;
    }
}
