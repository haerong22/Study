package org.example.userservice.model

data class SignUpRequest(
    val email: String,
    val password: String,
    val username: String,
)