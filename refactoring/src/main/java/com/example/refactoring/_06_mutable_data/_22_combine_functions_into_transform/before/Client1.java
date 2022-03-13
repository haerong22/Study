package com.example.refactoring._06_mutable_data._22_combine_functions_into_transform.before;

import java.time.Month;
import java.time.Year;

public class Client1 {

    double baseCharge;

    public Client1(Reading reading) {
        this.baseCharge = baseRate(reading.month(), reading.year()) * reading.quantity();
    }

    private double baseRate(Month month, Year year) {
        return 10;
    }

    public double getBaseCharge() {
        return baseCharge;
    }
}
