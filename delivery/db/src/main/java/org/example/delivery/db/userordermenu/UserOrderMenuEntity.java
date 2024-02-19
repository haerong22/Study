package org.example.delivery.db.userordermenu;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.example.delivery.db.BaseEntity;
import org.example.delivery.db.userordermenu.enums.UserOrderMenuStatus;

@Getter
@Entity
@Table(name = "user_order_menu")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class UserOrderMenuEntity extends BaseEntity {

    @Column(nullable = false)
    private Long userOrderId;

    @Column(nullable = false)
    private Long storeMenuId;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private UserOrderMenuStatus status;

    public UserOrderMenuEntity order() {
        this.status = UserOrderMenuStatus.REGISTERED;
        return this;
    }
}
