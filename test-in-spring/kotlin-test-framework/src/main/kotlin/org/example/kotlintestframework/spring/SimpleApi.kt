package org.example.kotlintestframework.spring

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SimpleApi(
    private val simpleUseCase: SimpleUseCase,
) {

    @GetMapping("/api/simple")
    fun getInfo(): String {
        if (simpleUseCase.exist()) {
            return simpleUseCase.execute()
        }
        return "NOT FOUND"
    }
}