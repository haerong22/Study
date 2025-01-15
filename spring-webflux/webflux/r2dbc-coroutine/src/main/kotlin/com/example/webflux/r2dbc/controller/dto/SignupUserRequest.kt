package com.example.webflux.r2dbc.controller.dto

data class SignupUserRequest(
    val name: String,
    val age: Int,
    val password: String,
    val profileImageId: String,
)