package org.example.paymentservice.wallet

import spock.lang.Specification

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
}
