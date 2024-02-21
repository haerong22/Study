package org.example.delivery.db.userordermenu;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.example.delivery.db.BaseEntity;
import org.example.delivery.db.storemenu.StoreMenuEntity;
import org.example.delivery.db.userorder.UserOrderEntity;
import org.example.delivery.db.userordermenu.enums.UserOrderMenuStatus;

@Getter
@Entity
@Table(name = "user_order_menu")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class UserOrderMenuEntity extends BaseEntity {

    @JoinColumn(nullable = false, name = "user_order_id")
    @ManyToOne
    private UserOrderEntity userOrder;

    @JoinColumn(nullable = false, name = "store_menu_id")
    @ManyToOne
    private StoreMenuEntity storeMenu;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private UserOrderMenuStatus status;

    public UserOrderMenuEntity order() {
        this.status = UserOrderMenuStatus.REGISTERED;
        return this;
    }
}
