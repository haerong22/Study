package com.example.refactoring._04_long_parameter_list._15_remove_flag_argument;

import java.time.LocalDate;

public class Shipment {

    public LocalDate deliveryDate(Order order, boolean isRush) {
        if (isRush) {
            return rushDeliveryDate(order);
        } else {
            return regularDeliveryDate(order);
        }
    }

    public LocalDate regularDeliveryDate(Order order) {
        int deliveryTime = switch (order.getDeliveryState()) {
            case "WA", "CA" -> 2;
            case "OR", "TX", "NY" -> 3;
            default -> 4;
        };
        return order.getPlacedOn().plusDays(deliveryTime);
    }

    public LocalDate rushDeliveryDate(Order order) {
        int deliveryTime = switch (order.getDeliveryState()) {
            case "WA", "CA", "OR" -> 1;
            case "TX", "NY", "FL" -> 2;
            default -> 3;
        };
        return order.getPlacedOn().plusDays(deliveryTime);
    }
}
