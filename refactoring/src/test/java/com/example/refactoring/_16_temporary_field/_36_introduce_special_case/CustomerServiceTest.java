package com.example.refactoring._16_temporary_field._36_introduce_special_case;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceTest {

    CustomerService customerService = new CustomerService();
    Customer unknown = new Customer("unknown", null, null);

    BillingPlan billingPlan = new BillingPlan();
    Customer kim = new Customer("kim", billingPlan, new PaymentHistory(1));

    @Test
    void customerName() {
        String unknownCustomerName = customerService.customerName(new Site(unknown));
        assertEquals("occupant", unknownCustomerName);

        String customer = customerService.customerName(new Site(kim));
        assertEquals("kim", customer);
    }

    @Test
    void billingPlan() {
        assertTrue((customerService.billingPlan(new Site(unknown)) instanceof BasicBillingPlan));

        assertEquals(billingPlan, customerService.billingPlan(new Site(kim)));
    }

    @Test
    void weeksDelingquent() {
        assertEquals(1, customerService.weeksDelinquent(new Site(kim)));

        assertEquals(0, customerService.weeksDelinquent(new Site(unknown)));
    }


}