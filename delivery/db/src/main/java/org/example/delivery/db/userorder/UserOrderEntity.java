package org.example.delivery.db.userorder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.example.delivery.db.BaseEntity;
import org.example.delivery.db.store.StoreEntity;
import org.example.delivery.db.userorder.enums.UserOrderStatus;
import org.example.delivery.db.userordermenu.UserOrderMenuEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@Table(name = "user_order")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@ToString(exclude = {"userOrderMenuList"})
public class UserOrderEntity extends BaseEntity {

    @Column(nullable = false)
    private Long userId;

    @JoinColumn(nullable = false, name = "store_id")
    @ManyToOne
    private StoreEntity store;

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

    @OneToMany(mappedBy = "userOrder")
    @JsonIgnore
    private List<UserOrderMenuEntity> userOrderMenuList;

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
