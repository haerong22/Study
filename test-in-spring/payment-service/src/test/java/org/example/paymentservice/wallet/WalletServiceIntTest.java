package org.example.paymentservice.wallet;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class WalletServiceIntTest {

    @Autowired
    private WalletService walletService;

    @Test
    @Transactional
    void 지갑을_생성한다() {
        // given
        var request = new CreateWalletRequest(200L);

        // when
        var response = walletService.createWallet(request);

        // then
        assertNotNull(response);
    }
}
