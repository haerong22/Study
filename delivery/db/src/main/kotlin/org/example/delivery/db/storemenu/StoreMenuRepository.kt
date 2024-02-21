package org.example.delivery.db.storemenu

import org.example.delivery.db.storemenu.enums.StoreMenuStatus
import org.springframework.data.jpa.repository.JpaRepository

interface StoreMenuRepository : JpaRepository<StoreMenuEntity, Long> {
    /**
     * SELECT * FROM store_menu WHERE id = ? AND status = ? ORDER BY id DESC LIMIT 1;
     */
    fun findFirstByIdAndStatusOrderByIdDesc(id: Long?, status: StoreMenuStatus?): StoreMenuEntity?

    /**
     * SELECT * FROM store_menu WHERE store_id = ? AND status = ? ORDER BY sequence DESC;
     */
    fun findAllByStoreIdAndStatusOrderBySequenceDesc(storeId: Long?, status: StoreMenuStatus?): List<StoreMenuEntity>
}
