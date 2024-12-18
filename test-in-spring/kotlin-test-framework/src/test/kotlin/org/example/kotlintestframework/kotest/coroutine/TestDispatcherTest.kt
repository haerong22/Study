package org.example.kotlintestframework.kotest.coroutine

import io.kotest.core.spec.style.FunSpec
import io.kotest.core.test.testCoroutineScheduler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalStdlibApi::class, ExperimentalCoroutinesApi::class)
class TestDispatcherTest : FunSpec({

    test("advance time") {
        val duration = 5.seconds
        launch {
            delay(duration.inWholeMilliseconds)
            println(LocalDateTime.now())
        }
        println(LocalDateTime.now())
    }

    test("advance time coroutine test scope").config(coroutineTestScope = true) {
        val duration = 5.seconds
        launch {
            delay(duration.inWholeMilliseconds)
            println(LocalDateTime.now())
        }
        println(LocalDateTime.now())

        println(testCoroutineScheduler.currentTime)
        testCoroutineScheduler.advanceTimeBy(duration.inWholeMilliseconds)
        println(testCoroutineScheduler.currentTime)
    }
})