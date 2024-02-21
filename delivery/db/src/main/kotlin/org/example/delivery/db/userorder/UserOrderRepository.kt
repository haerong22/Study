package org.example.delivery.db.userorder

import org.example.delivery.db.userorder.enums.UserOrderStatus
import org.springframework.data.jpa.repository.JpaRepository

interface UserOrderRepository : JpaRepository<UserOrderEntity, Long> {
    /**
     * SELECT * FROM user_order WHERE user_id = ? AND status = ? ORDER BY id DESC;
     */
    fun findAllByUserIdAndStatusOrderByIdDesc(userId: Long?, status: UserOrderStatus?): List<UserOrderEntity>

    /**
     * SELECT * FROM user_order WHERE user_id = ? AND status IN (?, ?, ... ) ORDER BY id DESC;
     */
    fun findAllByUserIdAndStatusInOrderByIdDesc(
        userId: Long?,
        statusList: List<UserOrderStatus>?
    ): List<UserOrderEntity>

    /**
     * SELECt * FROM user_order WHERE id = ? AND status = ? AND user_id = ?;
     */
    fun findByIdAndStatusAndUserId(id: Long?, status: UserOrderStatus?, userId: Long?): UserOrderEntity?

    /**
     * SELECt * FROM user_order WHERE id = ? AND user_id = ?;
     */
    fun findByIdAndUserId(id: Long?, userId: Long?): UserOrderEntity?
}
