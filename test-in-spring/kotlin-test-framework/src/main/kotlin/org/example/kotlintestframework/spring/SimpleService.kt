package org.example.kotlintestframework.spring

import org.springframework.stereotype.Service

@Service
class SimpleService: SimpleUseCase {

    override fun exist(): Boolean {
        return true
    }

    override fun execute(): String {
        return "Hello, World!"
    }

    override fun create(request: Request): String {
        if (request.name.isEmpty()) {
            throw IllegalArgumentException("name 이 공백")
        }
        return request.name
    }
}