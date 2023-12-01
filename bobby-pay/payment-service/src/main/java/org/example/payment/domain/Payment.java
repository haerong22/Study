package org.example.payment.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.util.Date;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Payment {

    private final Long paymentId;

    private final String requestMembershipId;

    private final int requestPrice;

    private final String franchiseId;

    private final String franchiseFeeRate;

    private final int paymentStatus;

    private final Date approvedAt;

    public static Payment generatePayment(
            PaymentId paymentId,
            RequestMembershipId requestMembershipId,
            RequestPrice requestPrice,
            FranchiseId franchiseId,
            FranchiseFeeRate franchiseFeeRate,
            PaymentStatus paymentStatus,
            ApprovedAt approvedAt
    ) {
        return new Payment(
                paymentId.getPaymentId(),
                requestMembershipId.getRequestMembershipId(),
                requestPrice.getRequestPrice(),
                franchiseId.getFranchiseId(),
                franchiseFeeRate.getFranchiseFeeRate(),
                paymentStatus.getPaymentStatus(),
                approvedAt.getApprovedAt()
        );
    }

    @Value
    public static class PaymentId {

        Long paymentId;

        public PaymentId(Long value) {
            this.paymentId = value;
        }
    }

    @Value
    public static class RequestMembershipId {

        String requestMembershipId;

        public RequestMembershipId(String value) {
            this.requestMembershipId = value;
        }
    }

    @Value
    public static class RequestPrice {

        int requestPrice;

        public RequestPrice(int value) {
            this.requestPrice = value;
        }
    }

    @Value
    public static class FranchiseId {

        String franchiseId;

        public FranchiseId(String value) {
            this.franchiseId = value;
        }
    }

    @Value
    public static class FranchiseFeeRate {

        String franchiseFeeRate;

        public FranchiseFeeRate(String value) {
            this.franchiseFeeRate = value;
        }
    }

    @Value
    public static class PaymentStatus {

        int paymentStatus;

        public PaymentStatus(int value) {
            this.paymentStatus = value;
        }
    }

    @Value
    public static class ApprovedAt {

        Date approvedAt;

        public ApprovedAt(Date value) {
            this.approvedAt = value;
        }
    }
}
