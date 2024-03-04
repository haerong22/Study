package org.example.delivery.account.domain.token.business

import org.example.delivery.account.domain.token.controller.model.TokenValidationRequest
import org.example.delivery.account.domain.token.controller.model.TokenValidationResponse
import org.example.delivery.account.domain.token.service.TokenService
import org.example.delivery.common.annotation.Business

@Business
class TokenBusiness(
    private val tokenService: TokenService
) {

    fun tokenValidation(tokenValidationRequest: TokenValidationRequest?): TokenValidationResponse {
        val result = tokenService.validationToken(tokenValidationRequest?.tokenDto?.token)

        return TokenValidationResponse(
            userId = result
        )
    }
}