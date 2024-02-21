package org.example.delivery.api.domain.userorder.converter

import org.example.delivery.api.domain.user.model.User
import org.example.delivery.api.domain.userorder.controller.model.UserOrderResponse
import org.example.delivery.common.annotation.Converter
import org.example.delivery.db.store.StoreEntity
import org.example.delivery.db.storemenu.StoreMenuEntity
import org.example.delivery.db.userorder.UserOrderEntity

@Converter
class UserOrderConverter {

    fun toEntity(
        user: User?,
        storeEntity: StoreEntity,
        storeMenuEntityList: List<StoreMenuEntity>?
    ): UserOrderEntity {
        val totalAmount = storeMenuEntityList
            ?.mapNotNull { it.amount }
            ?.reduce { acc, bigDecimal -> acc.add(bigDecimal) }

        return UserOrderEntity(
            userId = user?.id,
            store = storeEntity,
            amount = totalAmount,
        )
    }

    fun toResponse(
        userOrderEntity: UserOrderEntity
    ): UserOrderResponse {

        return UserOrderResponse(
            id = userOrderEntity.id,
            status = userOrderEntity.status,
            amount = userOrderEntity.amount,
            orderedAt = userOrderEntity.orderedAt,
            acceptedAt = userOrderEntity.acceptedAt,
            cookingStartAt = userOrderEntity.cookingStartAt,
            deliveryStartedAt = userOrderEntity.deliveryStartedAt,
            receivedAt = userOrderEntity.receivedAt,
        )
    }
}