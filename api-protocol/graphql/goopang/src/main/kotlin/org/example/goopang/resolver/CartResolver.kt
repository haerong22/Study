package org.example.goopang.resolver

import org.example.goopang.entity.cart.Cart
import org.example.goopang.input.AddCartItemInput
import org.example.goopang.input.DeleteCartItemInput
import org.example.goopang.service.CartService
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class CartResolver(
    private val cartService: CartService
) {

    @QueryMapping
    fun getUserCart(
        @Argument userId: String,
    ): Cart {
        return cartService.getUserCart(userId)
    }

    @MutationMapping
    fun addCartItem(
        @Argument addCartItemInput: AddCartItemInput,
    ): Cart {
        return cartService.addCartItem(addCartItemInput)
    }

    @MutationMapping
    fun deleteCartItem(
        @Argument deleteCartItemInput: DeleteCartItemInput,
    ): Cart {
        return cartService.deleteCartItem(deleteCartItemInput)
    }
}