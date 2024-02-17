package org.example.delivery.db.store;

import org.example.delivery.db.store.enums.StoreCategory;
import org.example.delivery.db.store.enums.StoreStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<StoreEntity, Long> {

    /**
     * SELECT * FROM store WHERE id = ? AND status = ? ORDER BY id DESC LIMIT 1;
     */
    Optional<StoreEntity> findFirstByIdAndStatusOrderByIdDesc(Long id, StoreStatus status);

    /**
     * SELECT * FROM store WHERE status = ? ORDER BY id DESC;
     */
    List<StoreEntity> findAllByStatusOrderByIdDesc(StoreStatus status);

    /**
     * SELECT * FROM store WHERE status = ? AND category = ? ORDER BY star DESC;
     */
    List<StoreEntity> findAllByStatusAndCategoryOrderByStarDesc(StoreStatus status, StoreCategory category);

    /**
     * SELECT * FROM store WHERE name = ? AND status = ? ORDER BY id DESC;
     */
    Optional<StoreEntity> findFirstByNameAndStatusOrderByIdDesc(String name, StoreStatus status);
}
