package org.example.delivery.account.domain.token.controller.model

import org.example.delivery.account.domain.token.model.TokenDto

data class TokenValidationRequest(
    var tokenDto: TokenDto?=null
)
