package com.example.order.infrastructure.order.payment;

import com.example.order.common.exception.InvalidParamException;
import com.example.order.domain.order.Order;
import com.example.order.domain.order.OrderCommand;
import com.example.order.domain.order.payment.PaymentProcessor;
import com.example.order.domain.order.payment.validator.PaymentValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentProcessorImpl implements PaymentProcessor {
    private final List<PaymentValidator> paymentValidatorList;
    private final List<PaymentApiCaller> paymentApiCallerList;

    @Override
    public void pay(Order order, OrderCommand.PaymentRequest paymentRequest) {
        paymentValidatorList.forEach(paymentValidator -> paymentValidator.validate(order, paymentRequest));
        PaymentApiCaller payApiCaller = routingApiCaller(paymentRequest);
        payApiCaller.pay(paymentRequest);
    }

    private PaymentApiCaller routingApiCaller(OrderCommand.PaymentRequest request) {
        return paymentApiCallerList.stream()
                .filter(paymentApiCaller -> paymentApiCaller.support(request.getPayMethod()))
                .findFirst()
                .orElseThrow(InvalidParamException::new);
    }
}