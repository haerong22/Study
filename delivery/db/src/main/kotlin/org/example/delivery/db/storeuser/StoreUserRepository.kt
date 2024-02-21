package org.example.delivery.db.storeuser

import org.example.delivery.db.storeuser.enums.StoreUserStatus
import org.springframework.data.jpa.repository.JpaRepository

interface StoreUserRepository : JpaRepository<StoreUserEntity, Long> {
    /**
     * SELECT * FROM store_user WHERE email = ? AND status = ? ORDER BY id DESC LIMIT 1
     */
    fun findFirstByEmailAndStatusOrderByIdDesc(email: String?, status: StoreUserStatus?): StoreUserEntity?
}
