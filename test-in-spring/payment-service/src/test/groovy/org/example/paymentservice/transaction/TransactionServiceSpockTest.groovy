package org.example.paymentservice.transaction

import org.example.paymentservice.wallet.AddBalanceWalletResponse
import org.example.paymentservice.wallet.FindWalletResponse
import org.example.paymentservice.wallet.WalletService
import spock.lang.Specification

import java.time.LocalDateTime

class TransactionServiceSpockTest extends Specification {
    TransactionService transactionService
    WalletService walletService = Mock()
    TransactionRepository transactionRepository = Mock()

    def setup() {
        transactionService = new TransactionService(walletService, transactionRepository)
    }

    def "충전 트랜잭션이 성공한다."() {
        given:
        def request = new ChargeTransactionRequest(1L, "orderId", BigDecimal.TEN)
        transactionRepository.findTransactionByOrderId(request.orderId()) >> Optional.empty()

        def findWalletResponse = new FindWalletResponse(1L, 1L, BigDecimal.ZERO, LocalDateTime.now(), LocalDateTime.now())
        walletService.findWalletByUserId(1L) >> findWalletResponse

        def addBalanceWalletResponse = new AddBalanceWalletResponse(1L, 1L, findWalletResponse.balance().add(request.amount()),)
        walletService.addBalance(_) >> addBalanceWalletResponse

        when:
        def response = transactionService.charge(request)

        then:
        1 * transactionRepository.save(_)
        response != null
        println response
    }

    def "지갑이 없다면 충전이 실패한다."() {
        given:
        def request = new ChargeTransactionRequest(1L, "orderId", BigDecimal.TEN)
        transactionRepository.findTransactionByOrderId(request.orderId()) >> Optional.empty()

        walletService.findWalletByUserId(1L) >> null

        when:
        transactionService.charge(request)

        then:
        def ex = thrown(RuntimeException)
        ex != null
        ex.message == "사용자 지갑이 존재하지 않습니다."
    }

    def "이미 충전됐다면 충전 트랜잭션이 실패한다."() {
        given:
        def request = new ChargeTransactionRequest(1L, "orderId", BigDecimal.TEN)
        transactionRepository.findTransactionByOrderId(request.orderId()) >> Optional.of(new Transaction())
        walletService.findWalletByUserId(1L) >> new FindWalletResponse(1L, 1L, BigDecimal.ZERO, LocalDateTime.now(), LocalDateTime.now())

        when:
        transactionService.charge(request)

        then:
        def ex = thrown(RuntimeException)
        ex != null
        ex.message == "이미 충전된 거래입니다."
    }

    def "결제 트랜잭션이 성공한다."() {
        given:
        def request = new PaymentTransactionRequest(1L, "100", BigDecimal.TEN)
        transactionRepository.findTransactionByOrderId(request.courseId()) >> Optional.empty()

        def findWalletResponse = new FindWalletResponse(1L, 1L, BigDecimal.TEN, LocalDateTime.now(), LocalDateTime.now())
        walletService.findWalletByWalletId(1L) >> findWalletResponse

        def addBalanceWalletResponse = new AddBalanceWalletResponse(1L, 1L, findWalletResponse.balance().add(request.amount().negate()),)
        walletService.addBalance(_) >> addBalanceWalletResponse

        when:
        def response = transactionService.payment(request)

        then:
        1 * transactionRepository.save(_)
        response != null
        println response
    }

    def "지갑이 없다면 결제가 실패한다."() {
        given:
        def request = new PaymentTransactionRequest(1L, "courseId", BigDecimal.TEN)
        transactionRepository.findTransactionByOrderId(request.courseId()) >> Optional.empty()

        walletService.findWalletByWalletId(1L) >> null

        when:
        transactionService.payment(request)

        then:
        def ex = thrown(RuntimeException)
        ex != null
        ex.message == "사용자 지갑이 존재하지 않습니다."
    }

    def "이미 결제됐다면 결제 트랜잭션이 실패한다."() {
        given:
        def request = new PaymentTransactionRequest(1L, "courseId", BigDecimal.TEN)
        transactionRepository.findTransactionByOrderId(request.courseId()) >> Optional.of(new Transaction())
        walletService.findWalletByWalletId(1L) >> new FindWalletResponse(1L, 1L, BigDecimal.ZERO, LocalDateTime.now(), LocalDateTime.now())

        when:
        transactionService.payment(request)

        then:
        def ex = thrown(RuntimeException)
        ex != null
        ex.message == "이미 결제 된 강좌입니다."
    }
}