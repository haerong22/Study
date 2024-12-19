package org.example.kotlintestframework.spring

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
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

    @PostMapping("/api/simple")
    fun postInfo(@RequestBody request: Request) {
        simpleUseCase.create(request)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(e: IllegalArgumentException) {}
}

data class Request(
    val name: String
)