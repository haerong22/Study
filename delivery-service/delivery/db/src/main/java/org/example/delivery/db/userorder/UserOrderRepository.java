package org.example.delivery.db.userorder;

import org.example.delivery.db.userorder.enums.UserOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserOrderRepository extends JpaRepository<UserOrderEntity, Long> {

    /**
     * SELECT * FROM user_order WHERE user_id = ? AND status = ? ORDER BY id DESC;
     */
    List<UserOrderEntity> findAllByUserIdAndStatusOrderByIdDesc(Long userId, UserOrderStatus status);

    /**
     * SELECT * FROM user_order WHERE user_id = ? AND status IN (?, ?, ... ) ORDER BY id DESC;
     */
    List<UserOrderEntity> findAllByUserIdAndStatusInOrderByIdDesc(Long userId, List<UserOrderStatus> statusList);

    /**
     * SELECt * FROM user_order WHERE id = ? AND status = ? AND user_id = ?;
     */
    Optional<UserOrderEntity> findByIdAndStatusAndUserId(Long id, UserOrderStatus status, Long userId);

    /**
     * SELECt * FROM user_order WHERE id = ? AND user_id = ?;
     */
    Optional<UserOrderEntity> findByIdAndUserId(Long id, Long userId);
}
