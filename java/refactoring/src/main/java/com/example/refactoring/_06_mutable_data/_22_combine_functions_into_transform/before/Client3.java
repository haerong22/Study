package com.example.refactoring._06_mutable_data._22_combine_functions_into_transform.before;

import java.time.Month;
import java.time.Year;

public class Client3 {

    private double basicChargeAmount;

    public Client3(Reading reading) {
        this.basicChargeAmount = calculateBaseCharge(reading);
    }

    private double calculateBaseCharge(Reading reading) {
        return baseRate(reading.month(), reading.year()) * reading.quantity();
    }

    private double baseRate(Month month, Year year) {
        return 10;
    }

    public double getBasicChargeAmount() {
        return basicChargeAmount;
    }
}
