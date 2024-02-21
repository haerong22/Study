package org.example.delivery.db.storemenu;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.example.delivery.db.BaseEntity;
import org.example.delivery.db.store.StoreEntity;
import org.example.delivery.db.storemenu.enums.StoreMenuStatus;

import java.math.BigDecimal;

@Getter
@Entity
@Table(name = "store_menu")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class StoreMenuEntity extends BaseEntity {

    @JoinColumn(nullable = false, name = "store_id")
    @ManyToOne
    private StoreEntity store;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(precision = 11, scale = 4, nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private StoreMenuStatus status;

    @Column(length = 200, nullable = false)
    private String thumbnailUrl;

    private int likeCount;

    private int sequence;

    public StoreMenuEntity register() {
        this.status = StoreMenuStatus.REGISTERED;
        return this;
    }
}
