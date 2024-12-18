package org.example.kotlintestframework.relaxedmock

import io.kotest.matchers.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class RelaxedMockExample2 {
    @MockK(relaxed = true)
    lateinit var car1: RelaxedCar

    @RelaxedMockK
    lateinit var car2: RelaxedCar

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun testCar1() {
        val result = car1.drive("NORTH")

        result shouldBe ""

        verify { car1.drive("NORTH") }
    }

    @Test
    fun testCar2() {
        val result = car2.drive("NORTH")

        result shouldBe ""

        verify { car2.drive("NORTH") }
    }
}