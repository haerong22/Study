package org.example.paymentservice.external;

import org.example.paymentservice.checkout.ConfirmRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class PaymentGatewayServiceIntTest {

    @Autowired
    private PaymentGatewayService paymentGatewayService;

    @Test
    void test() {
        paymentGatewayService.confirm(
                new ConfirmRequest(
                        "tgen_20241126194857NktA4",
                        "21f77626-ddc9-467a-8992-e0d6470339ed",
                        "30000"
                )
        );
    }
}