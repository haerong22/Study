package org.example.elsgraphql.service.impl;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.example.elsenrollment.domain.service.EnrollmentServiceGrpc;
import org.example.elsenrollment.domain.service.EnrollmentServiceOuterClass;
import org.example.elsenrollment.domain.service.FakePaymentServiceGrpc;
import org.example.elsgraphql.model.Enrollment;
import org.example.elsgraphql.model.Payment;
import org.example.elsgraphql.model.PlanSubscription;
import org.example.elsgraphql.service.EnrollmentService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Primary
@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    @GrpcClient("els-enrollment")
    private EnrollmentServiceGrpc.EnrollmentServiceBlockingStub enrollmentServiceStub;

    @GrpcClient("els-payment")
    private FakePaymentServiceGrpc.FakePaymentServiceBlockingStub paymentServiceStub;

    @Override
    public List<Enrollment> getEnrollmentsByUserId(Long userId) {
        EnrollmentServiceOuterClass.UserEnrollmentsRequest request = EnrollmentServiceOuterClass.UserEnrollmentsRequest.newBuilder()
                .setUserId(userId)
                .build();

        EnrollmentServiceOuterClass.UserEnrollmentsResponse response = enrollmentServiceStub.getUserEnrollments(request);

        return response.getEnrollmentsList().stream()
                .map(Enrollment::fromProto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PlanSubscription> getSubscriptionsByUserId(Long userId) {
        EnrollmentServiceOuterClass.UserSubscriptionsRequest request = EnrollmentServiceOuterClass.UserSubscriptionsRequest.newBuilder()
                .setUserId(userId)
                .build();

        EnrollmentServiceOuterClass.UserSubscriptionsResponse response = enrollmentServiceStub.getUserPlanSubscriptions(request);

        return response.getSubscriptionsList().stream()
                .map(PlanSubscription::fromProto)
                .collect(Collectors.toList());
    }

    @Override
    public Payment purchaseCourse(long userId, long courseId, double amount, String paymentMethod) {
        Payment paymentResponse = createPayment(userId, "COURSE", amount, paymentMethod);
        registerCourse(userId, courseId, paymentResponse.getId());
        return paymentResponse;
    }

    @Override
    public Payment purchaseSubscription(long userId, double amount, String paymentMethod) {
        Payment paymentResponse = createPayment(userId, "SUBSCRIPTION", amount, paymentMethod);
        manageSubscription(userId, System.currentTimeMillis(), System.currentTimeMillis() + 31536000000L, paymentResponse.getId());
        return paymentResponse;
    }

    @Override
    public boolean checkCourseAccess(long courseId, long userId) {
        EnrollmentServiceOuterClass.CourseAccessRequest request = EnrollmentServiceOuterClass.CourseAccessRequest.newBuilder()
                .setCourseId(courseId)
                .setUserId(userId)
                .build();

        EnrollmentServiceOuterClass.CourseAccessResponse response = enrollmentServiceStub.checkCourseAccess(request);

        return response.getHasAccess();
    }

    @Override
    public boolean checkSubscriptionAccess(long userId) {
        EnrollmentServiceOuterClass.SubscriptionAccessRequest request = EnrollmentServiceOuterClass.SubscriptionAccessRequest.newBuilder()
                .setUserId(userId)
                .build();

        EnrollmentServiceOuterClass.SubscriptionAccessResponse response = enrollmentServiceStub.checkSubscriptionAccess(request);

        return response.getHasAccess();
    }

    @Cacheable(value = "payment", key = "#paymentId")
    @Override
    public Payment findPaymentById(Long paymentId) {
        EnrollmentServiceOuterClass.PaymentsByIdRequest request = EnrollmentServiceOuterClass.PaymentsByIdRequest.newBuilder()
                .setPaymentId(paymentId)
                .build();

        EnrollmentServiceOuterClass.PaymentsByIdResponse response = paymentServiceStub.getPaymentsByPaymentId(request);

        return Payment.fromProto(response.getPayment());
    }

    private Payment createPayment(long userId, String type, double amount, String paymentMethod) {
        EnrollmentServiceOuterClass.PaymentRequest request = EnrollmentServiceOuterClass.PaymentRequest.newBuilder()
                .setUserId(userId)
                .setType(type)
                .setAmount(amount)
                .setPaymentMethod(paymentMethod)
                .build();

        EnrollmentServiceOuterClass.PaymentResponse response = paymentServiceStub.createPayment(request);

        return Payment.fromProto(response);
    }

    private EnrollmentServiceOuterClass.CourseRegistrationResponse registerCourse(long userId, long courseId, long paymentId) {
        EnrollmentServiceOuterClass.CourseRegistrationRequest request = EnrollmentServiceOuterClass.CourseRegistrationRequest.newBuilder()
                .setUserId(userId)
                .setCourseId(courseId)
                .setPaymentId(paymentId)
                .build();

        return enrollmentServiceStub.registerCourse(request);
    }

    private EnrollmentServiceOuterClass.SubscriptionResponse manageSubscription(long userId, long startDate, long endDate, long paymentId) {
        EnrollmentServiceOuterClass.SubscriptionRequest request = EnrollmentServiceOuterClass.SubscriptionRequest.newBuilder()
                .setUserId(userId)
                .setStartDate(startDate)
                .setEndDate(endDate)
                .setPaymentId(paymentId)
                .build();

        return enrollmentServiceStub.manageSubscription(request);
    }


}
