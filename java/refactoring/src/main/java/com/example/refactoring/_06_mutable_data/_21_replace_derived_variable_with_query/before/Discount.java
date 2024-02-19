package com.example.refactoring._06_mutable_data._21_replace_derived_variable_with_query.before;

public class Discount {

    private double discountedTotal;
    private double discount;

    private double baseTotal;

    public Discount(double baseTotal) {
        this.baseTotal = baseTotal;
    }

    public double getDiscountedTotal() {
        return this.discountedTotal;
    }

    public void setDiscount(double number) {
        this.discount = number;
        this.discountedTotal = this.baseTotal - this.discount;
    }
}
