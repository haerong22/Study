package org.example.goopang.repository

import org.example.goopang.entity.cart.Cart
import org.example.goopang.entity.cart.CartItem
import org.example.goopang.entity.product.Clothing
import org.example.goopang.entity.product.Electronics
import org.example.goopang.entity.user.User
import java.time.OffsetDateTime

object Database {

    val products = arrayListOf(
        Electronics(
            id = "1",
            name = "macbook air",
            price = 1000.0,
            warrantyPeriod = "3years",
        ),
        Electronics(
            id = "2",
            name = "iphone 19",
            price = 2000.0,
            warrantyPeriod = "2years",
        ),
        Electronics(
            id = "3",
            name = "samsung tv",
            price = 3000.0,
            warrantyPeriod = "1years",
        ),
        Clothing(
            id = "4",
            name = "T-shirt",
            price = 300.0,
            size = "M",
        ),
        Clothing(
            id = "5",
            name = "Jeans",
            price = 200.0,
            size = "L",
        ),
        Clothing(
            id = "6",
            name = "Dress",
            price = 100.0,
            size = "S",
        ),
    )

    val users = arrayListOf(
        User(
            id = "1",
            name = "burger",
            email = "burger@goopang.com",
            createdAt = OffsetDateTime.now().minusDays(30),
        ),
        User(
            id = "2",
            name = "Jane",
            email = "jane@goopang.com",
            createdAt = OffsetDateTime.now().minusDays(60),
        ),
    )

    val carts = arrayListOf(
        Cart(
            id = "1",
            user = users.first { it.id == "1" },
        ),
        Cart(
            id = "2",
            user = users.first { it.id == "2" },
        ),
    )

    val cartItems = arrayListOf(
        CartItem(
            id = "1",
            quantity = 1,
            product = products.first { it.id == "1" },
            cart = carts.first { it.id == "1" },
        ),
        CartItem(
            id = "2",
            quantity = 3,
            product = products.first { it.id == "3" },
            cart = carts.first { it.id == "1" },
        ),
        CartItem(
            id = "3",
            quantity = 2,
            product = products.first { it.id == "5" },
            cart = carts.first { it.id == "1" },
        ),
        CartItem(
            id = "4",
            quantity = 1,
            product = products.first { it.id == "6" },
            cart = carts.first { it.id == "2" },
        ),
        CartItem(
            id = "5",
            quantity = 2,
            product = products.first { it.id == "2" },
            cart = carts.first { it.id == "2" },
        ),
    )
}