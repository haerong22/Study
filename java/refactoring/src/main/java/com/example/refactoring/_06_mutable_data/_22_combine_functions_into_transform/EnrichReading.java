package com.example.refactoring._06_mutable_data._22_combine_functions_into_transform;

public record EnrichReading(Reading reading, double baseCharge, double taxableCharge) {
}
