package org.example.kotlintestframework.mockk.mock

import io.kotest.core.spec.style.FunSpec
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class MockkExample: FunSpec({
    val car2 = mockk<Car>()

    test("mockk test") {
        val car1 = mockk<Car>()

        every { car1.drive("NORTH") } returns "OK"

        car1.drive("NORTH")

        verify { car1.drive("NORTH") }

        confirmVerified(car1)
    }
})

