package org.example.delivery.db.store

import org.example.delivery.db.store.enums.StoreCategory
import org.example.delivery.db.store.enums.StoreStatus
import org.springframework.data.jpa.repository.JpaRepository

interface StoreRepository : JpaRepository<StoreEntity, Long> {
    /**
     * SELECT * FROM store WHERE id = ? AND status = ? ORDER BY id DESC LIMIT 1;
     */
    fun findFirstByIdAndStatusOrderByIdDesc(id: Long?, status: StoreStatus?): StoreEntity?

    /**
     * SELECT * FROM store WHERE status = ? ORDER BY id DESC;
     */
    fun findAllByStatusOrderByIdDesc(status: StoreStatus?): List<StoreEntity>

    /**
     * SELECT * FROM store WHERE status = ? AND category = ? ORDER BY star DESC;
     */
    fun findAllByStatusAndCategoryOrderByStarDesc(status: StoreStatus?, category: StoreCategory?): List<StoreEntity>

    /**
     * SELECT * FROM store WHERE name = ? AND status = ? ORDER BY id DESC;
     */
    fun findFirstByNameAndStatusOrderByIdDesc(name: String?, status: StoreStatus?): StoreEntity?
}
