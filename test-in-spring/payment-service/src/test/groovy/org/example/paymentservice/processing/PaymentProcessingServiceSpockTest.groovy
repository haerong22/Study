package org.example.paymentservice.processing

import com.fasterxml.jackson.databind.ObjectMapper
import org.example.paymentservice.checkout.ConfirmRequest
import org.example.paymentservice.external.PaymentGatewayService
import org.example.paymentservice.order.Order
import org.example.paymentservice.order.OrderRepository
import org.example.paymentservice.retry.RetryRequestRepository
import org.example.paymentservice.transaction.TransactionService
import org.springframework.web.client.RestClientException
import spock.lang.Specification

class PaymentProcessingServiceSpockTest extends Specification {
    private PaymentProcessingService paymentProcessingService
    private PaymentGatewayService paymentGatewayService = Mock()
    private TransactionService transactionService = Mock()
    private OrderRepository orderRepository = Mock()
    private RetryRequestRepository retryRequestRepository = Mock()
    private ObjectMapper objectMapper = new ObjectMapper()

    def setup() {
        paymentProcessingService = new PaymentProcessingService(
                paymentGatewayService,
                transactionService,
                orderRepository,
                objectMapper,
                retryRequestRepository,
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
        paymentProcessingService.createCharge(confirmRequest, false)

        then:
        1 * paymentGatewayService.confirm(confirmRequest)
        1 * transactionService.charge(_)
        1 * orderRepository.save(order)
    }

    def "Retry & 결제 성공 시 충전된다."() {
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
        paymentProcessingService.createCharge(confirmRequest, true)

        then:
        1 * paymentGatewayService.confirm(confirmRequest)
        1 * transactionService.charge(_)
        1 * orderRepository.save(order)
    }

    def "Timeout 이 발생했을때 RetryRequest 를 저장하고 오류를 발생시킨다."() {
        given:
        ConfirmRequest confirmRequest = new ConfirmRequest(
                "paymentKey",
                "orderId",
                "1000"
        )

        // mock
        Order order = new Order()
        orderRepository.findByRequestId(confirmRequest.orderId()) >> order
        paymentGatewayService.confirm(_) >> {
            RestClientException ex = new RestClientException(
                    'Error while extracting response for type [java.lang.Object] and content type [application/octet-stream]',
                    new SocketTimeoutException("Read timed out")
            )
            throw ex
        }

        when:
        paymentProcessingService.createCharge(confirmRequest, false)

        then:
        def ex = thrown(RestClientException)
        ex.printStackTrace()
        0 * transactionService.charge(_)
        0 * orderRepository.save(order)
        1 * retryRequestRepository.save(_)
    }
}