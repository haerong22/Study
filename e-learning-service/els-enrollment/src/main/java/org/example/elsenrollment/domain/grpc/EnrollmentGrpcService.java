package org.example.elsenrollment.domain.grpc;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.example.elsenrollment.domain.entity.Enrollment;
import org.example.elsenrollment.domain.entity.Subscription;
import org.example.elsenrollment.domain.service.EnrollmentService;
import org.example.elsenrollment.domain.service.EnrollmentServiceGrpc;
import org.example.elsenrollment.domain.service.EnrollmentServiceOuterClass;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@GrpcService
@Slf4j
@RequiredArgsConstructor
public class EnrollmentGrpcService extends EnrollmentServiceGrpc.EnrollmentServiceImplBase {

    private final EnrollmentService enrollmentService;

    @Override
    public void registerCourse(EnrollmentServiceOuterClass.CourseRegistrationRequest request, StreamObserver<EnrollmentServiceOuterClass.CourseRegistrationResponse> responseObserver) {
        try {
            Enrollment enrollment = enrollmentService.registerCourse(request.getUserId(), request.getCourseId(), request.getPaymentId());

            EnrollmentServiceOuterClass.CourseRegistrationResponse response = EnrollmentServiceOuterClass.CourseRegistrationResponse.newBuilder()
                    .setCourseId(enrollment.getCourseId())
                    .setUserId(enrollment.getUserId())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("registerCourse error : ", e);
            responseObserver.onError(e);
        }
    }

    @Override
    public void manageSubscription(EnrollmentServiceOuterClass.SubscriptionRequest request, StreamObserver<EnrollmentServiceOuterClass.SubscriptionResponse> responseObserver) {
        try {
            LocalDateTime startDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(request.getStartDate()), ZoneId.systemDefault());
            LocalDateTime endDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(request.getEndDate()), ZoneId.systemDefault());

            Subscription subscription = enrollmentService.manageSubscription(
                    request.getUserId(),
                    startDate,
                    endDate,
                    request.getPaymentId()
            );

            EnrollmentServiceOuterClass.SubscriptionResponse response = EnrollmentServiceOuterClass.SubscriptionResponse.newBuilder()
                    .setSubscription(subscription.toProto())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("manageSubscription error : ", e);
            responseObserver.onError(e);
        }
    }

    @Override
    public void renewSubscription(EnrollmentServiceOuterClass.RenewSubscriptionRequest request, StreamObserver<EnrollmentServiceOuterClass.SubscriptionResponse> responseObserver) {
        try {
            LocalDateTime startDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(request.getStartDate()), ZoneId.systemDefault());
            LocalDateTime endDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(request.getEndDate()), ZoneId.systemDefault());

            Subscription subscription = enrollmentService.renewSubscription(
                    request.getSubscriptionId(),
                    startDate,
                    endDate
            );

            EnrollmentServiceOuterClass.SubscriptionResponse response = EnrollmentServiceOuterClass.SubscriptionResponse.newBuilder()
                    .setSubscription(subscription.toProto())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("renewSubscription error : ", e);
            responseObserver.onError(e);
        }
    }

    @Override
    public void checkCourseAccess(EnrollmentServiceOuterClass.CourseAccessRequest request, StreamObserver<EnrollmentServiceOuterClass.CourseAccessResponse> responseObserver) {
        boolean hasAccess = enrollmentService.checkCourseAccess(request.getUserId(), request.getCourseId());

        EnrollmentServiceOuterClass.CourseAccessResponse response = EnrollmentServiceOuterClass.CourseAccessResponse.newBuilder()
                .setHasAccess(hasAccess)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void checkSubscriptionAccess(EnrollmentServiceOuterClass.SubscriptionAccessRequest request, StreamObserver<EnrollmentServiceOuterClass.SubscriptionAccessResponse> responseObserver) {
        boolean hasAccess = enrollmentService.checkSubscriptionAccess(request.getUserId(), LocalDateTime.now());

        EnrollmentServiceOuterClass.SubscriptionAccessResponse response = EnrollmentServiceOuterClass.SubscriptionAccessResponse.newBuilder()
                .setHasAccess(hasAccess)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getUserEnrollments(EnrollmentServiceOuterClass.UserEnrollmentsRequest request, StreamObserver<EnrollmentServiceOuterClass.UserEnrollmentsResponse> responseObserver) {
        List<Enrollment> enrollments = enrollmentService.getUserEnrollments(request.getUserId());

        EnrollmentServiceOuterClass.UserEnrollmentsResponse.Builder responseBuilder = EnrollmentServiceOuterClass.UserEnrollmentsResponse.newBuilder();

        for (Enrollment enrollment : enrollments) {
            responseBuilder.addEnrollments(enrollment.toProto());
        }

        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void getUserPlanSubscriptions(EnrollmentServiceOuterClass.UserSubscriptionsRequest request, StreamObserver<EnrollmentServiceOuterClass.UserSubscriptionsResponse> responseObserver) {
        List<Subscription> subscriptions = enrollmentService.getUserPlanSubscriptions(request.getUserId());

        EnrollmentServiceOuterClass.UserSubscriptionsResponse response = EnrollmentServiceOuterClass.UserSubscriptionsResponse.newBuilder()
                .addAllSubscriptions(subscriptions.stream().map(Subscription::toProto).toList())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}