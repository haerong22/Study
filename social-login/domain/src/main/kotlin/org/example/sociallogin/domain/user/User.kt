package org.example.sociallogin.domain.user

import org.example.sociallogin.domain.social.OauthProvider

class User(
    val name: String,
    val email: String,
    val socialId: String,
    val provider: OauthProvider,
)