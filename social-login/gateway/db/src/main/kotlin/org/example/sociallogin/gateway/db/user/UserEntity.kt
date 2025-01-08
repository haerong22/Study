package org.example.sociallogin.gateway.db.user

import org.example.sociallogin.domain.user.User
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table(name = "`user`")
class UserEntity(
    @Id
    val id: Long? = null,
    val name: String,
    val email: String,
    val googleId: String
) {

    fun toDomain(): User = User(
        name = name,
        email = email,
        googleId = googleId,
    )
}