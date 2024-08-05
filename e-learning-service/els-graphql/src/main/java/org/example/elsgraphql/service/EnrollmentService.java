package org.example.elsgraphql.service;

import org.example.elsgraphql.model.Enrollment;
import org.example.elsgraphql.model.Payment;
import org.example.elsgraphql.model.PlanSubscription;

import java.util.List;

public interface EnrollmentService {
    List<Enrollment> getEnrollmentsByUserId(Long userId);

    List<PlanSubscription> getSubscriptionsByUserId(Long userId);

    Payment purchaseCourse(long userId, long courseId, double amount, String paymentMethod);

    Payment purchaseSubscription(long userId, double amount, String paymentMethod);

    boolean checkCourseAccess(long courseId, long userId);

    boolean checkSubscriptionAccess(long userId);

    Payment findPaymentById(Long paymentId);
}
