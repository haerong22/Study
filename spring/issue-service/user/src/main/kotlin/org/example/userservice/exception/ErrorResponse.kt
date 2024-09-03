package org.example.userservice.exception

data class ErrorResponse(
    val code: Int,
    val message: String,
)
