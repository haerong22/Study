package org.example.paymentservice.processing;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.example.paymentservice.checkout.ConfirmRequest;
import org.example.paymentservice.external.PaymentGatewayService;
import org.example.paymentservice.order.Order;
import org.example.paymentservice.order.OrderRepository;
import org.example.paymentservice.retry.RetryRequest;
import org.example.paymentservice.retry.RetryRequestRepository;
import org.example.paymentservice.transaction.ChargeTransactionRequest;
import org.example.paymentservice.transaction.TransactionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;

import java.math.BigDecimal;
import java.net.SocketTimeoutException;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentProcessingService {
    private final PaymentGatewayService paymentGatewayService;
    private final TransactionService transactionService;
    private final OrderRepository orderRepository;
    private final ObjectMapper objectMapper;
    private final RetryRequestRepository retryRequestRepository;

    @Transactional
    public void createPayment(ConfirmRequest confirmRequest) {
        paymentGatewayService.confirm(confirmRequest);
        transactionService.pgPayment();
        final Order order = orderRepository.findByRequestId(confirmRequest.orderId());
        approveOrder(order);
    }

    @Transactional
    public void createCharge(ConfirmRequest confirmRequest, boolean isRetry) {
        try {
            paymentGatewayService.confirm(confirmRequest);
        } catch (Exception e) {
            log.error("caught exception on createCharge", e);
            if (!isRetry && e instanceof RestClientException &&
                    e.getCause() instanceof SocketTimeoutException) {
                createRetryRequest(confirmRequest, e);
            }
            throw e;
        }

        final Order order = orderRepository.findByRequestId(confirmRequest.orderId());
        transactionService.charge(
                new ChargeTransactionRequest(
                        order.getUserId(),
                        confirmRequest.orderId(),
                        new BigDecimal(confirmRequest.amount())
                )
        );
        approveOrder(order);
    }

    @SneakyThrows
    private void createRetryRequest(ConfirmRequest confirmRequest, Exception e) {
        RetryRequest retryRequest = new RetryRequest(
                objectMapper.writeValueAsString(confirmRequest),
                confirmRequest.orderId(),
                e.getMessage(),
                RetryRequest.Type.CONFIRM
        );
        retryRequestRepository.save(retryRequest);
    }

    private void approveOrder(Order order) {
        order.setStatus(Order.Status.APPROVED);
        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(order);
    }
}