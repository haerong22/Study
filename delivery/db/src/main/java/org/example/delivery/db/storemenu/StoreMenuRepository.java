package org.example.delivery.db.storemenu;

import org.example.delivery.db.storemenu.enums.StoreMenuStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreMenuRepository extends JpaRepository<StoreMenuEntity, Long> {

    /**
     * SELECT * FROM store_menu WHERE id = ? AND status = ? ORDER BY id DESC LIMIT 1;
     */
    Optional<StoreMenuEntity> findFirstByIdAndStatusOrderByIdDesc(Long id, StoreMenuStatus status);

    /**
     * SELECT * FROM store_menu WHERE store_id = ? AND status = ? ORDER BY sequence DESC;
     */
    List<StoreMenuEntity> findAllByStoreIdAndStatusOrderBySequenceDesc(Long storeId, StoreMenuStatus status);
}
