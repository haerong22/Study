package org.example.elsgraphql.controller;

import lombok.RequiredArgsConstructor;
import org.example.elsgraphql.model.Enrollment;
import org.example.elsgraphql.model.Payment;
import org.example.elsgraphql.model.PlanSubscription;
import org.example.elsgraphql.service.EnrollmentService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @QueryMapping
    public List<Enrollment> getUserEnrollments(
            @Argument Long userId
    ) {
        return enrollmentService.getEnrollmentsByUserId(userId);
    }

    @QueryMapping
    public List<PlanSubscription> getUserPlanSubscriptions(
            @Argument Long userId
    ) {
        return enrollmentService.getSubscriptionsByUserId(userId);
    }

    @QueryMapping
    public boolean checkCourseAccess(
            @Argument Long userId,
            @Argument Long courseId
    ) {
        return enrollmentService.checkSubscriptionAccess(userId)
                || enrollmentService.checkCourseAccess(courseId, userId);

    }

    @MutationMapping
    public Payment purchaseCourse(
            @Argument Long userId,
            @Argument Long courseId,
            @Argument Float amount,
            @Argument String paymentMethod
    ) {
        return enrollmentService.purchaseCourse(userId, courseId, amount, paymentMethod);
    }

    @MutationMapping
    public Payment purchaseSubscription(
            @Argument Long userId,
            @Argument Float amount,
            @Argument String paymentMethod
    ) {
        return enrollmentService.purchaseSubscription(userId, amount, paymentMethod);
    }
}
