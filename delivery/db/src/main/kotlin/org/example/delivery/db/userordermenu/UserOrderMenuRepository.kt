package org.example.delivery.db.userordermenu

import org.example.delivery.db.userordermenu.enums.UserOrderMenuStatus
import org.springframework.data.jpa.repository.JpaRepository

interface UserOrderMenuRepository : JpaRepository<UserOrderMenuEntity, Long> {
    /**
     * SELECT * FROM user_order_menu WHERE user_order_id = ? AND status = ?;
     */
    fun findAllByUserOrderIdAndStatus(userOrderId: Long?, status: UserOrderMenuStatus?): List<UserOrderMenuEntity>
}
