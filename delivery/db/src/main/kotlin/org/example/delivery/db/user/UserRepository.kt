package org.example.delivery.db.user

import org.example.delivery.db.user.enums.UserStatus
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<UserEntity, Long> {

    /**
     * SELECT * FROM users WHERE id = ? AND status = ? ORDER BY id DESC limit 1;
     */
    fun findFirstByIdAndStatusOrderByIdDesc(userId: Long?, status: UserStatus?): UserEntity?

    /**
     * SELECT * FROM users WHERE email = ? AND password = ? AND status = ? ORDER BY id DESC limit 1;
     */
    fun findFirstByEmailAndPasswordAndStatusOrderByIdDesc(
        email: String?,
        password: String?,
        status: UserStatus?
    ): UserEntity?
}