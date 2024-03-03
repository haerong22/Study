package org.example.delivery.api.domain.userorder.business

import org.example.delivery.api.common.Log
import org.example.delivery.api.domain.store.converter.StoreConverter
import org.example.delivery.api.domain.store.service.StoreService
import org.example.delivery.api.domain.storemenu.converter.StoreMenuConverter
import org.example.delivery.api.domain.storemenu.service.StoreMenuService
import org.example.delivery.api.domain.user.model.User
import org.example.delivery.api.domain.userorder.controller.model.UserOrderDetailResponse
import org.example.delivery.api.domain.userorder.controller.model.UserOrderRequest
import org.example.delivery.api.domain.userorder.controller.model.UserOrderResponse
import org.example.delivery.api.domain.userorder.converter.UserOrderConverter
import org.example.delivery.api.domain.userorder.producer.UserOrderProducer
import org.example.delivery.api.domain.userorder.service.UserOrderService
import org.example.delivery.api.domain.userordermenu.converter.UserOrderMenuConverter
import org.example.delivery.api.domain.userordermenu.service.UserOrderMenuService
import org.example.delivery.common.annotation.Business
import org.example.delivery.db.userordermenu.enums.UserOrderMenuStatus

@Business
class UserOrderBusiness(
    private val userOrderService: UserOrderService,
    private val userOrderConverter: UserOrderConverter,

    private val storeMenuService: StoreMenuService,
    private val storeMenuConverter: StoreMenuConverter,

    private val userOrderMenuService: UserOrderMenuService,
    private val userOrderMenuConverter: UserOrderMenuConverter,

    private val storeService: StoreService,
    private val storeConverter: StoreConverter,

    private val userOrderProducer: UserOrderProducer,
) {

    companion object : Log

    fun userOrder(
        user: User?,
        userOrderRequest: UserOrderRequest?,
    ): UserOrderResponse {
        val storeEntity = storeService.getStoreWithThrow(userOrderRequest?.storeId)

        val storeMenuEntityList = userOrderRequest?.storeMenuIdList
            ?.mapNotNull { storeMenuService.getStoreMenuWithThrow(it) }
            ?.toList()

        val ordered = userOrderConverter.toEntity(
            user = null,
            storeEntity = storeEntity,
            storeMenuEntityList = storeMenuEntityList
        ).run {
            userOrderService.order(this)
        }

        val userOrderMenuEntityList = storeMenuEntityList
            ?.map { userOrderMenuConverter.toEntity(ordered, it) }
            ?.toList()

        userOrderMenuEntityList?.forEach { userOrderMenuService.order(it) }

        userOrderProducer.sendOrder(ordered)

        return userOrderConverter.toResponse(ordered)
    }

    fun current(
        user: User?
    ): List<UserOrderDetailResponse> {

        return userOrderService.current(user?.id).map { userOrder ->
            log.info("사용자의 주문 정보 : {}", userOrder)

            val storeMenuEntityList = userOrder.userOrderMenuList
                ?.filter { userOrderMenu -> userOrderMenu.status == UserOrderMenuStatus.REGISTERED }
                ?.map { userOrderMenu -> userOrderMenu.storeMenu }
                ?.toList()

            val storeEntity = userOrder.store

            UserOrderDetailResponse(
                userOrderResponse = userOrderConverter.toResponse(userOrder),
                storeResponse = storeConverter.toResponse(storeEntity),
                storeMenuResponseList = storeMenuConverter.toResponse(storeMenuEntityList)
            )
        }
    }

    fun history(
        user: User?
    ): List<UserOrderDetailResponse> {

        return userOrderService.history(user?.id).map { userOrder ->
            log.info("사용자의 주문 정보 : {}", userOrder)

            val storeMenuEntityList = userOrder.userOrderMenuList
                ?.filter { userOrderMenu -> userOrderMenu.status == UserOrderMenuStatus.REGISTERED }
                ?.map { userOrderMenu -> userOrderMenu.storeMenu }
                ?.toList()

            val storeEntity = userOrder.store

            UserOrderDetailResponse(
                userOrderResponse = userOrderConverter.toResponse(userOrder),
                storeResponse = storeConverter.toResponse(storeEntity),
                storeMenuResponseList = storeMenuConverter.toResponse(storeMenuEntityList)
            )
        }
    }

    fun read(
        user: User?,
        orderId: Long?,
    ): UserOrderDetailResponse {

        return userOrderService.getUserOrderWithOutStatusWithThrow(orderId, user?.id)
            .let { userOrder ->
                val storeMenuEntityList = userOrder.userOrderMenuList
                    ?.filter { userOrderMenu -> userOrderMenu.status == UserOrderMenuStatus.REGISTERED }
                    ?.map { userOrderMenu -> userOrderMenu.storeMenu }
                    ?.toList()
                    ?: listOf()

                val storeEntity = userOrder.store

                UserOrderDetailResponse(
                    userOrderResponse = userOrderConverter.toResponse(userOrder),
                    storeResponse = storeConverter.toResponse(storeEntity),
                    storeMenuResponseList = storeMenuConverter.toResponse(storeMenuEntityList)
                )
            }
    }

}