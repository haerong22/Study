package org.example.delivery.account.domain.token.interfaces

import org.example.delivery.account.domain.token.model.TokenDto

interface TokenHelper {

    fun issueAccessToken(data: Map<String, Any>): TokenDto?
    fun issueRefreshToken(data: Map<String, Any>): TokenDto?
    fun validationTokenWithThrow(token: String?): Map<String, Any>?
}