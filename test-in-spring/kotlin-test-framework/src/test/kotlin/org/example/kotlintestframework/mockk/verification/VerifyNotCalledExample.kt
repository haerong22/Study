package org.example.kotlintestframework.mockk.verification

import io.kotest.core.spec.style.FunSpec
import io.mockk.Called
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.example.kotlintestframework.mockk.Car

class VerifyNotCalledExample: FunSpec({

    test("verify not called") {
        val car1 = mockk<Car>()
        val car2 = mockk<Car>()

        every { car1.drive("NORTH") } returns "ok"
        every { car1.drive("SOUTH") } returns "ok"
        every { car1.drive("WEST") } returns "ok"

        car1.drive("NORTH")
        car1.drive("SOUTH")

        verify(exactly = 0) { car1.drive("WEST") }
        verify { car2 wasNot Called }
    }
})