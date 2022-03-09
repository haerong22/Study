package com.example.refactoring._06_mutable_data._19_separate_query_from_modifier;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BillingTest {

    @Test
    void totalOutstanding() {
        Billing billing = new Billing(new Customer("kim", List.of(new Invoice(20), new Invoice(30))),
                new EmailGateway());
        assertEquals(50d, billing.getTotalOutstanding());

        billing.sendBill();
    }

}