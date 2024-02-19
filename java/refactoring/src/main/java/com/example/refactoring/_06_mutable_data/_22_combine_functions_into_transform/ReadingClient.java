package com.example.refactoring._06_mutable_data._22_combine_functions_into_transform;

import java.time.Month;
import java.time.Year;

public class ReadingClient {

    protected double taxThreshold(Year year) {
        return 5;
    }

    protected double baseRate(Month month, Year year) {
        return 10;
    }

    protected EnrichReading enrichReading(Reading reading) {
        return new EnrichReading(reading, baseCharge(reading), taxableCharge(reading));
    }

    private double taxableCharge(Reading reading) {
        return Math.max(0, baseCharge(reading) - taxThreshold(reading.year()));
    }

    private double baseCharge(Reading reading) {
        return baseRate(reading.month(), reading.year()) * reading.quantity();
    }
}
