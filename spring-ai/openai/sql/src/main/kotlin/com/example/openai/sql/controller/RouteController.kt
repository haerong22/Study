package com.example.openai.sql.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class RouteController {

    @GetMapping("/sql")
    fun index(): String {
        return "sql"
    }
}