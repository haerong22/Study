package org.example.sociallogin.gateway.db.user

import org.example.sociallogin.domain.social.OauthProvider
import org.example.sociallogin.domain.user.User
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table(name = "`user`")
class UserEntity(
    @Id
    val id: Long? = null,
    val name: String,
    val email: String,
    val socialId: String,
    val provider: OauthProvider,
) {

    fun toDomain(): User = User(
        name = name,
        email = email,
        socialId = socialId,
        provider = provider,
    )
}