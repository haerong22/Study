package org.example.paymentservice.processing;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.paymentservice.checkout.ConfirmRequest;
import org.example.paymentservice.external.PaymentGatewayService;
import org.example.paymentservice.order.Order;
import org.example.paymentservice.order.OrderRepository;
import org.example.paymentservice.transaction.TransactionService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentProcessingService {
    private final PaymentGatewayService paymentGatewayService;
    private final TransactionService transactionService;
    private final OrderRepository orderRepository;

    public void createPayment(ConfirmRequest confirmRequest) {
        paymentGatewayService.confirm(confirmRequest);
        transactionService.pgPayment();
        approveOrder(confirmRequest.orderId());
    }

    private void approveOrder(String orderId) {
        final Order order = orderRepository.findByRequestId(orderId);
        order.setStatus(Order.Status.APPROVED);
        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(order);
    }
}