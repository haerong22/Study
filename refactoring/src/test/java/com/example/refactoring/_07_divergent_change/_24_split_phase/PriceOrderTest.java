package com.example.refactoring._07_divergent_change._24_split_phase;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PriceOrderTest {

    @Test
    void priceOrder_discountedFee() {
        PriceOrder priceOrder = new PriceOrder();
        double price = priceOrder.priceOrder(new Product(10, 2, 0.5),
                4,
                new ShippingMethod(20, 1, 5));

        // basePrice = 10 * 4 = 40
        // discount = 2 * 10 * 0.5 = 10
        // shippingPerCase = 1 (40 > 20)
        // shippingCost = 4 * 1 = 4
        // price = 40 - 10 + 4 = 34

        assertEquals(34, price);
    }

    @Test
    void priceOrder_feePerCase() {
        PriceOrder priceOrder = new PriceOrder();
        double price = priceOrder.priceOrder(new Product(10, 2, 0.5),
                2,
                new ShippingMethod(20, 1, 5));

        // basePrice = 10 * 2 = 20
        // discount = 0 * 10 * 0.5 = 0
        // shippingPerCase = 5
        // shippingCost = 2 * 5 = 10
        // price = 20 - 0 + 10 = 30

        assertEquals(30, price);
    }
}