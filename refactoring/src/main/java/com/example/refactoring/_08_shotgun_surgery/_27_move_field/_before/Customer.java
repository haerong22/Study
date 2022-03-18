package com.example.refactoring._08_shotgun_surgery._27_move_field._before;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Customer {

    private String name;

    private double discountRate;

    private CustomerContract contract;

    public Customer(String name, double discountRate) {
        this.name = name;
        this.discountRate = discountRate;
        this.contract = new CustomerContract(dateToday());
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public void becomePreferred() {
        this.discountRate += 0.03;
        // 다른 작업들
    }

    public double applyDiscount(double amount) {
        BigDecimal value = BigDecimal.valueOf(amount);
        return value.subtract(value.multiply(BigDecimal.valueOf(this.discountRate))).doubleValue();
    }

    private LocalDate dateToday() {
        return LocalDate.now();
    }
}
