package org.example.issueservice.exception

data class ErrorResponse(
    val code: Int,
    val message: String,
)
