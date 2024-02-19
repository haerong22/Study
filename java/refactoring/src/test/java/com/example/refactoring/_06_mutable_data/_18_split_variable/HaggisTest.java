package com.example.refactoring._06_mutable_data._18_split_variable;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HaggisTest {

    @Test
    void distance() {
        Haggis haggis = new Haggis(10d, 20d, 10, 5);
        assertEquals(50d, haggis.distanceTravelled(10));
        assertEquals(125d, haggis.distanceTravelled(20));
    }

}