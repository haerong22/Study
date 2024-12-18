package org.example.kotlintestframework.mockk.mock

import io.kotest.core.spec.style.FunSpec
import io.mockk.every
import io.mockk.spyk
import io.mockk.verify

class SpykExample : FunSpec({

    test("spyk test") {
        val car = spyk<Car>()

        every { car.drive("NORTH") } returns "ok"

        car.drive("NORTH")

        verify { car.drive("NORTH") }
    }
})