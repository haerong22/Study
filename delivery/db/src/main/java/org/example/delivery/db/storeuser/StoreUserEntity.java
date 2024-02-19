package org.example.delivery.db.storeuser;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.example.delivery.db.BaseEntity;
import org.example.delivery.db.storeuser.enums.StoreUserRole;
import org.example.delivery.db.storeuser.enums.StoreUserStatus;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "store_user")
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class StoreUserEntity extends BaseEntity {

    @Column(nullable = false)
    private Long storeId;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private StoreUserStatus status;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private StoreUserRole role;

    private LocalDateTime registeredAt;

    private LocalDateTime unregisteredAt;

    private LocalDateTime lastLoginAt;


    public StoreUserEntity register(String encoded) {
        this.status = StoreUserStatus.REGISTERED;
        this.password = encoded;
        this.registeredAt = LocalDateTime.now();
        return this;
    }

}
