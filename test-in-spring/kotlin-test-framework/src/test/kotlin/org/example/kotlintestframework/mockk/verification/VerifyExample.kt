package org.example.kotlintestframework.mockk.verification

import io.kotest.core.spec.style.FunSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.example.kotlintestframework.mockk.Car

class VerifyExample: FunSpec({

    test("verify") {
        val car = mockk<Car>()

        every { car.drive("NORTH") } returns "ok"
        every { car.drive("SOUTH") } returns "ok"
        every { car.drive("WEST") } returns "ok"

        car.drive("NORTH")
        car.drive("NORTH")
        car.drive("SOUTH")
        car.drive("NORTH")

        verify {
            car.drive("NORTH")
            car.drive("SOUTH")
        }

        verify(exactly = 3) { car.drive("NORTH") }
        verify(atLeast = 1) { car.drive("NORTH") }
        verify(atMost = 5) { car.drive("NORTH") }
    }
})