package org.example.paymentservice.processing

import org.example.paymentservice.checkout.ConfirmRequest
import org.example.paymentservice.external.PaymentGatewayService
import org.example.paymentservice.order.Order
import org.example.paymentservice.order.OrderRepository
import org.example.paymentservice.transaction.TransactionService
import spock.lang.Specification

class PaymentProcessingServiceSpockTest extends Specification {
    private PaymentProcessingService paymentProcessingService
    private PaymentGatewayService paymentGatewayService = Mock()
    private TransactionService transactionService = Mock()
    private OrderRepository orderRepository = Mock()

    def setup() {
        paymentProcessingService = new PaymentProcessingService(
                paymentGatewayService,
                transactionService,
                orderRepository
        )
    }

    def "PG 결제 성공 시 결제기록이 생성된다."() {
        given:
        ConfirmRequest confirmRequest = new ConfirmRequest(
                "paymentKey",
                "orderId",
                "1000"
        )

        Order order = new Order()
        orderRepository.findByRequestId(confirmRequest.orderId()) >> order

        when:
        paymentProcessingService.createPayment(confirmRequest)

        then:
        1 * paymentGatewayService.confirm(confirmRequest)
        1 * transactionService.pgPayment()
        1 * orderRepository.save(order)
    }

    def "PG 를 통한 결제 성공 시 충전된다."() {
        given:
        ConfirmRequest confirmRequest = new ConfirmRequest(
                "paymentKey",
                "orderId",
                "1000"
        )

        // mock
        Order order = new Order()
        orderRepository.findByRequestId(confirmRequest.orderId()) >> order

        when:
        paymentProcessingService.createCharge(confirmRequest)

        then:
        1 * paymentGatewayService.confirm(confirmRequest)
        1 * transactionService.charge(_)
        1 * orderRepository.save(order)
    }

}