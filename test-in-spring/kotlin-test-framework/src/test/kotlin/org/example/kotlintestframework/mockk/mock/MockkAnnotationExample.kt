package org.example.kotlintestframework.mockk.mock

import io.mockk.MockKAnnotations
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MockkAnnotationExample {
    @MockK
    lateinit var car: Car

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun test() {
        every { car.drive("NORTH") } returns "ok"

        car.drive("NORTH")

        verify { car.drive("NORTH") }

        confirmVerified(car)
    }
}