package org.example.elsgraphql.resolver;

import lombok.RequiredArgsConstructor;
import org.example.elsgraphql.model.Payment;
import org.example.elsgraphql.service.UserService;
import org.example.elsgraphql.model.User;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class PaymentDataResolver {

    private final UserService userService;

    @SchemaMapping(typeName = "Payment", field = "user")
    public User getUser(Payment payment) {
        return userService.findById(payment.getUserId()).orElse(null);
    }
}