package org.example.goopang.service

import org.apache.coyote.BadRequestException
import org.example.goopang.entity.user.User
import org.example.goopang.input.AddUserInput
import org.example.goopang.repository.Database
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import java.util.UUID

@Service
class UserService(
    private val cartService: CartService,
) {

    fun getUser(userId: String): User {
        return Database.users.firstOrNull { it.id == userId }
            ?.also { user -> user.cart = cartService.getUserCart(userId) }
            ?: throw BadRequestException("User not found")
    }

    fun addUser(addUserInput: AddUserInput): User {
        return User(
            id = UUID.randomUUID().toString().substring(0, 5),
            name = addUserInput.name,
            email = addUserInput.email,
            createdAt = OffsetDateTime.now(),
        )
            .also { user -> Database.users.add(user) }
            .also { user -> user.cart = cartService.addUserCart(user) }
    }
}