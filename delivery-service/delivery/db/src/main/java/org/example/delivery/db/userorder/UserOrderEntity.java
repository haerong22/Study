package org.example.delivery.db.userorder;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.example.delivery.db.BaseEntity;
import org.example.delivery.db.userorder.enums.UserOrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "user_order")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class UserOrderEntity extends BaseEntity {

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long storeId;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private UserOrderStatus status;


    @Column(precision = 11, scale = 4, nullable = false)
    private BigDecimal amount;

    private LocalDateTime orderedAt;

    private LocalDateTime acceptedAt;

    private LocalDateTime cookingStartAt;

    private LocalDateTime deliveryStartedAt;

    private LocalDateTime receivedAt;

    public UserOrderEntity order() {
        this.status = UserOrderStatus.ORDER;
        this.orderedAt = LocalDateTime.now();
        return this;
    }

    public UserOrderEntity accept() {
        this.status = UserOrderStatus.ACCEPT;
        this.acceptedAt = LocalDateTime.now();
        return this;
    }

    public UserOrderEntity cooking() {
        this.status = UserOrderStatus.COOKING;
        this.cookingStartAt = LocalDateTime.now();
        return this;
    }

    public UserOrderEntity delivery() {
        this.status = UserOrderStatus.DELIVERY;
        this.deliveryStartedAt = LocalDateTime.now();
        return this;
    }

    public UserOrderEntity receive() {
        this.status = UserOrderStatus.RECEIVE;
        this.receivedAt = LocalDateTime.now();
        return this;
    }
}
