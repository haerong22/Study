package com.example.openai.sql.service

data class SqlResponse(
    val query: String,
    val results: List<Any>,
)
