package org.example.paymentservice.wallet

import spock.lang.Specification

import java.time.LocalDateTime

class WalletServiceSpockTest extends Specification {
    WalletService walletService
    WalletRepository walletRepository = Mock()

    def setup() {
        walletService = new WalletService(walletRepository)
        println "setup"
    }

    def "지갑 생성 요청 시 지갑을 가지고 있지 않다면 생성된다."() {
        given:
        def request = new CreateWalletRequest(1L)
        walletRepository.findByUserId(1L) >> Optional.empty()

        when:
        def createWallet = walletService.createWallet(request)

        then:
        1 * walletRepository.save(_) >> new Wallet(1L)
        createWallet != null
        createWallet.balance() == BigDecimal.ZERO
    }

    def "지갑 생성 요청 시 지갑을 이미 가지고 있다면 오류를 응답한다."() {
        given:
        def request = new CreateWalletRequest(1L)
        walletRepository.findByUserId(1L) >> Optional.of(new Wallet(1L))

        when:
        walletService.createWallet(request)

        then:
        def ex = thrown(RuntimeException)
        ex != null
        ex.message == "이미 지갑이 있습니다."
    }

    def "지갑을 조회한다. - 생성되어 있는 경우"() {
        given:
        def userId = 1L
        def wallet = new Wallet(userId)
        wallet.balance = new BigDecimal(1000)
        walletRepository.findByUserId(userId) >> Optional.of(wallet)

        when:
        def result = walletService.findWalletByUserId(userId)

        then:
        result != null
        result.balance() == new BigDecimal(1000)
    }

    def "지갑을 조회한다. - 생성되어 있지 않은 경우"() {
        given:
        def userId = 1L
        def wallet = new Wallet(userId)
        wallet.balance = new BigDecimal(1000)
        walletRepository.findByUserId(userId) >> Optional.empty()

        when:
        def result = walletService.findWalletByUserId(1L)
        then:
        result == null
    }

    def "지갑 잔액 추가 시 지갑이 존재하고 잔액이 충분하면 잔액이 업데이트 된다"() {
        given:
        def walletId = 1L
        def initialBalance = new BigDecimal("200.00")
        def addAmount = new BigDecimal("100.00")
        def wallet = new Wallet(walletId, walletId, initialBalance, LocalDateTime.now(), LocalDateTime.now())

        // mock
        walletRepository.findById(walletId) >> Optional.of(wallet)

        when:
        def result = walletService.addBalance(new AddBalanceWalletRequest(1L, addAmount))

        then:
        result.balance() == new BigDecimal("300.00")
    }

    def "지갑 잔액 추가 시 지갑이 존재하지 않으면 예외가 발생한다"() {
        given:
        def walletId = 999L
        def addAmount = new BigDecimal("100.00")
        walletRepository.findById(walletId) >> Optional.empty()

        when:
        walletService.addBalance(new AddBalanceWalletRequest(walletId, addAmount))

        then:
        def ex = thrown(RuntimeException)
        ex != null
        ex.printStackTrace()
    }

    def "지갑 잔액 추가 시 잔액이 부족하면 예외가 발생한다"() {
        given:
        def walletId = 1L
        def addAmount = new BigDecimal("-101.00")
        def initialBalance = new BigDecimal("100.00")
        def wallet = new Wallet(walletId, walletId, initialBalance, LocalDateTime.now(), LocalDateTime.now())
        walletRepository.findById(walletId) >> Optional.of(wallet)

        when:
        walletService.addBalance(new AddBalanceWalletRequest(walletId, addAmount))

        then:
        def ex = thrown(RuntimeException)
        ex != null
        ex.printStackTrace()
    }


    def "지갑 잔액 추가 시 한도를 초과하면 예외가 발생한다"() {
        given:
        def walletId = 1L
        def addAmount = new BigDecimal(100_000)
        def initialBalance = new BigDecimal(1)
        def wallet = new Wallet(walletId, walletId, initialBalance, LocalDateTime.now(), LocalDateTime.now())
        walletRepository.findById(walletId) >> Optional.of(wallet)

        when:
        walletService.addBalance(new AddBalanceWalletRequest(walletId, addAmount))

        then:
        def ex = thrown(RuntimeException)
        ex != null
        ex.printStackTrace()
    }
}
