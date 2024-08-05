package org.example.elsgraphql.resolver;

import lombok.RequiredArgsConstructor;
import org.example.elsgraphql.model.Payment;
import org.example.elsgraphql.model.PlanSubscription;
import org.example.elsgraphql.model.User;
import org.example.elsgraphql.service.EnrollmentService;
import org.example.elsgraphql.service.UserService;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class PlanSubscriptionDataResolver {

    private final UserService userService;
    private final EnrollmentService enrollmentService;

    @SchemaMapping(typeName = "PlanSubscription", field = "user")
    public User getUser(PlanSubscription subscription) {
        return userService.findById(subscription.getUserId()).orElse(null);
    }

    @SchemaMapping(typeName = "PlanSubscription", field = "payment")
    public Payment getPayment(PlanSubscription subscription) {
        return enrollmentService.findPaymentById(subscription.getPaymentId());
    }
}