package org.example.delivery.db.storeuser;

import org.example.delivery.db.storeuser.enums.StoreUserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreUserRepository extends JpaRepository<StoreUserEntity, Long> {

    /**
     * SELECT * FROM store_user WHERE email = ? AND status = ? ORDER BY id DESC LIMIT 1
     */
    Optional<StoreUserEntity> findFirstByEmailAndStatusOrderByIdDesc(String email, StoreUserStatus status);
}
