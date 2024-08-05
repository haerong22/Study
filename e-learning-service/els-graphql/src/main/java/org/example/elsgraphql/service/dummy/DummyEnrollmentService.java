package org.example.elsgraphql.service.dummy;

import lombok.extern.slf4j.Slf4j;
import org.example.elsgraphql.model.Enrollment;
import org.example.elsgraphql.model.Payment;
import org.example.elsgraphql.model.PlanSubscription;
import org.example.elsgraphql.service.EnrollmentService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DummyEnrollmentService implements EnrollmentService {

    private final List<Enrollment> enrollments = new ArrayList<>();
    private final List<PlanSubscription> subscriptions = new ArrayList<>();
    private final List<Payment> payments = new ArrayList<>();

    private final AtomicLong enrollmentIdGenerator = new AtomicLong(100);
    private final AtomicLong subscriptionIdGenerator = new AtomicLong(100);
    private final AtomicLong paymentIdGenerator = new AtomicLong(100);

    public DummyEnrollmentService() {
        initData();
    }

    private void initData() {
        // Initialize some dummy payments
        Long paymentId1 = paymentIdGenerator.getAndIncrement();
        Long paymentId2 = paymentIdGenerator.getAndIncrement();
        Long paymentId3 = paymentIdGenerator.getAndIncrement();
        Long paymentId4 = paymentIdGenerator.getAndIncrement();

        payments.add(new Payment(paymentIdGenerator.getAndIncrement(), 100L, null, "COURSE", 100.00f, "Credit Card", LocalDateTime.now().minusDays(10).toString()));
        payments.add(new Payment(paymentIdGenerator.getAndIncrement(), 100L, null, "SUBSCRIPTION", 120.00f, "PayPal", LocalDateTime.now().minusDays(15).toString()));

        // Initialize some dummy enrollments
        enrollments.add(new Enrollment(enrollmentIdGenerator.getAndIncrement(), 100L, null, 100L, null, paymentId1, null, LocalDateTime.now().minusDays(5).toString()));
        enrollments.add(new Enrollment(enrollmentIdGenerator.getAndIncrement(), 100L, null, 101L, null, paymentId2, null, LocalDateTime.now().minusDays(3).toString()));

        // Initialize some dummy subscriptions
        subscriptions.add(new PlanSubscription(subscriptionIdGenerator.getAndIncrement(), 101L, null, paymentId3, null, LocalDateTime.now().minusDays(300).toString(), LocalDateTime.now().plusDays(65).toString(), "Expired"));
        subscriptions.add(new PlanSubscription(subscriptionIdGenerator.getAndIncrement(), 102L, null, paymentId4, null, LocalDateTime.now().minusDays(200).toString(), LocalDateTime.now().plusDays(160).toString(), "Expired"));
    }

    @Override
    public List<Enrollment> getEnrollmentsByUserId(Long userId) {
        return enrollments.stream()
                .filter(enrollment -> enrollment.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public List<PlanSubscription> getSubscriptionsByUserId(Long userId) {
        return subscriptions.stream()
                .filter(subscription -> subscription.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public Payment purchaseCourse(long userId, long courseId, double amount, String paymentMethod) {
        Payment newPayment = new Payment(paymentIdGenerator.getAndIncrement(), userId, null, "COURSE", (float) amount, paymentMethod, LocalDateTime.now().toString());
        payments.add(newPayment);
        return newPayment;
    }

    @Override
    public Payment purchaseSubscription(long userId, double amount, String paymentMethod) {
        Payment newPayment = new Payment(paymentIdGenerator.getAndIncrement(), userId, null, "SUBSCRIPTION", (float) amount, paymentMethod, LocalDateTime.now().toString());
        payments.add(newPayment);
        return newPayment;
    }

    @Override
    public boolean checkCourseAccess(long courseId, long userId) {
        return enrollments.stream().anyMatch(e -> e.getUserId() == userId && e.getCourseId() == courseId);
    }

    @Override
    public boolean checkSubscriptionAccess(long userId) {
        LocalDateTime now = LocalDateTime.now();
        return subscriptions.stream().anyMatch(s -> s.getUserId() == userId && (now.isAfter(LocalDateTime.parse(s.getStartDate())) && now.isBefore(LocalDateTime.parse(s.getEndDate()))));
    }

    @Override
    public Payment findPaymentById(Long paymentId) {
        return payments.stream().filter(payment -> payment.getId().equals(paymentId)).findFirst().orElseThrow();
    }
}