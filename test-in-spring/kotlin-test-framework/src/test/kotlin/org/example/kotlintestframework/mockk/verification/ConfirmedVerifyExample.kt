package org.example.kotlintestframework.mockk.verification

import io.kotest.core.spec.style.FunSpec
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.example.kotlintestframework.mockk.Car

class ConfirmedVerifyExample: FunSpec({

    test("confirmed verify") {
        val car = mockk<Car>()

        every { car.drive(any()) } returns "ok"

        car.drive("NORTH")

        verify { car.drive("NORTH") }

        confirmVerified(car)
    }
})