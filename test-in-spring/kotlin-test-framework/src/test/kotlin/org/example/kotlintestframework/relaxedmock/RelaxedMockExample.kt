package org.example.kotlintestframework.relaxedmock

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.mockk
import io.mockk.verify

class RelaxedMockExample: FunSpec({

    test("relaxed mock") {
        val car = mockk<RelaxedCar>(relaxed = true)

        val result = car.drive("NORTH")

        result shouldBe ""

        verify { car.drive("NORTH") }
    }
})