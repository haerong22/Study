package org.example.paymentservice.checkout;

public record ConfirmRequest(String paymentKey, String orderId, String amount) {
}