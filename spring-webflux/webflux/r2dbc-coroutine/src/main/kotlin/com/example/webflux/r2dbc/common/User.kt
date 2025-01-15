package com.example.webflux.r2dbc.common

import java.util.Optional

data class User(
    val id: String,
    val name: String,
    val age: Int,
    val profileImage: Optional<Image>,
    val articleList: List<Article>,
    val followCount: Long,
)