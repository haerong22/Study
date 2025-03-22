package org.example.goopang.service

import org.apache.coyote.BadRequestException
import org.example.goopang.entity.cart.Cart
import org.example.goopang.entity.cart.CartItem
import org.example.goopang.entity.user.User
import org.example.goopang.input.AddCartItemInput
import org.example.goopang.input.DeleteCartItemInput
import org.example.goopang.repository.Database
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class CartService(
    private val productService: ProductService,
) {

    fun getUserCart(userId: String): Cart {
        return Database.carts.firstOrNull { it.user.id == userId }
            ?.also { cart -> cart.items = Database.cartItems.filter { it.cart.id == cart.id } }
            ?.also { cart -> cart.totalAmount = cart.items.sumOf { it.product.price * it.quantity } }
            ?: throw BadRequestException("Cart not found")
    }

    fun addUserCart(user: User): Cart {
        return Cart(
            id = UUID.randomUUID().toString().substring(0, 5),
            user = user
        )
            .also { cart -> Database.carts.add(cart) }
    }

    fun addCartItem(addCartItemInput: AddCartItemInput): Cart {
        CartItem(
            id = UUID.randomUUID().toString().substring(0, 5),
            quantity = addCartItemInput.quantity,
            product = productService.getProduct(addCartItemInput.productId),
            cart = getUserCart(addCartItemInput.userId),
        )
            .also { Database.cartItems.add(it) }

        return getUserCart(addCartItemInput.userId)
    }

    fun deleteCartItem(deleteCartItemInput: DeleteCartItemInput): Cart {
        Database.cartItems.removeIf { it.id == deleteCartItemInput.cartItemId }

        return getUserCart(deleteCartItemInput.userId)
    }
}