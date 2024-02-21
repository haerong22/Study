package org.example.delivery.db.userorder

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.example.delivery.db.store.StoreEntity
import org.example.delivery.db.userorder.enums.UserOrderStatus
import org.example.delivery.db.userordermenu.UserOrderMenuEntity
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "user_order")
class UserOrderEntity(

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @field:Column(nullable = false)
    var userId: Long? = null,

    @field:JoinColumn(nullable = false, name = "store_id")
    @field:ManyToOne
    var store: StoreEntity? = null,

    @field:Enumerated(EnumType.STRING)
    @field:Column(length = 50, nullable = false)
    var status: UserOrderStatus? = null,

    @field:Column(precision = 11, scale = 4, nullable = false)
    var amount: BigDecimal? = null,

    var orderedAt: LocalDateTime? = null,

    var acceptedAt: LocalDateTime? = null,

    var cookingStartAt: LocalDateTime? = null,

    var deliveryStartedAt: LocalDateTime? = null,

    var receivedAt: LocalDateTime? = null,

    @field:OneToMany(mappedBy = "userOrder")
    @field:JsonIgnore
    var userOrderMenuList: MutableList<UserOrderMenuEntity>? = null

) {
    fun order(): UserOrderEntity {
        this.status = UserOrderStatus.ORDER
        this.orderedAt = LocalDateTime.now()
        return this
    }

    fun accept(): UserOrderEntity {
        this.status = UserOrderStatus.ACCEPT
        this.acceptedAt = LocalDateTime.now()
        return this
    }

    fun cooking(): UserOrderEntity {
        this.status = UserOrderStatus.COOKING
        this.cookingStartAt = LocalDateTime.now()
        return this
    }

    fun delivery(): UserOrderEntity {
        this.status = UserOrderStatus.DELIVERY
        this.deliveryStartedAt = LocalDateTime.now()
        return this
    }

    fun receive(): UserOrderEntity {
        this.status = UserOrderStatus.RECEIVE
        this.receivedAt = LocalDateTime.now()
        return this
    }

    override fun toString(): String {
        return "UserOrderEntity(id=$id, userId=$userId, store=$store, status=$status, amount=$amount, orderedAt=$orderedAt, acceptedAt=$acceptedAt, cookingStartAt=$cookingStartAt, deliveryStartedAt=$deliveryStartedAt, receivedAt=$receivedAt)"
    }

}