package org.example.kotlintestframework.mockk.verification

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.example.kotlintestframework.mockk.Car

class VerifyWithArgExample: FunSpec({

    test("verify") {
        val car = mockk<Car>()

        every { car.drive(any()) } returns "ok"

        car.drive("NORTH")

        verify {
            car.drive(
                withArg { it shouldBe "NORTH" }
            )
        }
    }
})