package org.example.elsenrollment.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.example.elsenrollment.domain.service.EnrollmentServiceOuterClass;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
@Entity
@Table(name = "subscriptions")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(name = "payment_id", nullable = false)
    private Long paymentId;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", insertable = false, updatable = false)
    private Payment payment;

    public EnrollmentServiceOuterClass.Subscription toProto() {
        LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault());
        String status = (now.isAfter(this.startDate) && now.isBefore(this.endDate)) ? "Active" : "Expired";

        return EnrollmentServiceOuterClass.Subscription.newBuilder()
                .setSubscriptionId(this.id)
                .setUserId(this.userId)
                .setPaymentId(this.paymentId)
                .setStartDate(this.startDate.atZone(ZoneId.systemDefault()).toEpochSecond())
                .setEndDate(this.endDate.atZone(ZoneId.systemDefault()).toEpochSecond())
                .setStatus(status)
                .build();
    }
}