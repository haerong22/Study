package org.example.elsenrollment.domain.grpc;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.example.elsenrollment.domain.entity.Payment;
import org.example.elsenrollment.domain.entity.PaymentType;
import org.example.elsenrollment.domain.service.EnrollmentServiceOuterClass;
import org.example.elsenrollment.domain.service.FakePaymentServiceGrpc;
import org.example.elsenrollment.domain.service.PaymentService;

import java.math.BigDecimal;
import java.util.List;

@GrpcService
@RequiredArgsConstructor
public class PaymentGrpcService extends FakePaymentServiceGrpc.FakePaymentServiceImplBase {

    private final PaymentService paymentService;

    @Override
    public void createPayment(EnrollmentServiceOuterClass.PaymentRequest request, StreamObserver<EnrollmentServiceOuterClass.PaymentResponse> responseObserver) {
        try {
            Payment payment = paymentService.createPayment(
                    request.getUserId(),
                    PaymentType.valueOf(request.getType()),
                    BigDecimal.valueOf(request.getAmount()),
                    request.getPaymentMethod()
            );

            responseObserver.onNext(payment.toProto());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void listUserPayments(EnrollmentServiceOuterClass.UserPaymentsRequest request, StreamObserver<EnrollmentServiceOuterClass.UserPaymentsResponse> responseObserver) {
        List<Payment> payments = paymentService.getUserPayments(request.getUserId());

        EnrollmentServiceOuterClass.UserPaymentsResponse response = EnrollmentServiceOuterClass.UserPaymentsResponse.newBuilder()
                .addAllPayments(payments.stream().map(Payment::toProto).toList())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getPaymentsByPaymentId(EnrollmentServiceOuterClass.PaymentsByIdRequest request, StreamObserver<EnrollmentServiceOuterClass.PaymentsByIdResponse> responseObserver) {
        Payment payment = paymentService.getPaymentById(request.getPaymentId());

        EnrollmentServiceOuterClass.PaymentsByIdResponse response = EnrollmentServiceOuterClass.PaymentsByIdResponse.newBuilder()
                .setPayment(payment.toProto())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}