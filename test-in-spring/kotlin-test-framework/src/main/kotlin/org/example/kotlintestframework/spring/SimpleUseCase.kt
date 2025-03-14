package org.example.kotlintestframework.spring

interface SimpleUseCase {
    fun exist(): Boolean
    fun execute(): String
    fun create(request: Request): String
}