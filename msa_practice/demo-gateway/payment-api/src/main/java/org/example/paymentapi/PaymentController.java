package org.example.paymentapi;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class PaymentController {

    @GetMapping("/v1/payments/{id}")
    public ResponseEntity<Map<String, Object>> getPaymentsInfo(@PathVariable("id") Long paymentId) {
        return new ResponseEntity<>(
                Map.of(
                        "paymentId", paymentId,
                        "paymentInfo", Map.of("paymentMethod", "CARD"),
                        "timestamp", System.currentTimeMillis()
                ),
                HttpStatus.OK
        );
    }
}
