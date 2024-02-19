package com.example.refactoring._06_mutable_data._22_combine_functions_into_transform;

import org.junit.jupiter.api.Test;

import java.time.Month;
import java.time.Year;

import static org.junit.jupiter.api.Assertions.*;

class ReadingClientTest {

    @Test
    void client1() {
        Client1 client1 = new Client1(acquireReading());
        assertEquals(100d, client1.getBaseCharge());
    }

    @Test
    void client2() {
        Client2 client2 = new Client2(acquireReading());
        assertEquals(100d, client2.getBase());
        assertEquals(95d, client2.getTaxableCharge());
    }

    @Test
    void client3() {
        Client3 client3 = new Client3(acquireReading());
        assertEquals(100d, client3.getBasicChargeAmount());
    }


    private Reading acquireReading() {
        return new Reading("kim", 10, Month.MARCH, Year.of(2022));
    }

}