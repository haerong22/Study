package com.example.openai.sql.controller

import com.example.openai.sql.service.SqlResponse
import com.example.openai.sql.service.SqlService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class SqlController(
    private val sqlService: SqlService,
) {

    @PostMapping("/sql")
    fun queryForList(
        @RequestParam question: String,
    ): SqlResponse {
        return sqlService.queryForList(question)
    }
}