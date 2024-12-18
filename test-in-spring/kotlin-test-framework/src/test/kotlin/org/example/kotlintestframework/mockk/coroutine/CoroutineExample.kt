package org.example.kotlintestframework.mockk.coroutine

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import org.example.kotlintestframework.mockk.Car

class CoroutineExample: FunSpec({

    test("coroutine mock") {
        val car = mockk<Car>()

        coEvery { car.drive(any()) } returns "ok"

        car.drive("NORTH") shouldBe "ok"

        coVerify { car.drive("NORTH") }

        confirmVerified(car)
    }
})