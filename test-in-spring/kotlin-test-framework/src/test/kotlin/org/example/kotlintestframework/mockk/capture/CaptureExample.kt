package org.example.kotlintestframework.mockk.capture

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.example.kotlintestframework.mockk.Car

class CaptureExample: FunSpec({

    test("capture") {
        val car = mockk<Car>()
        val slot = slot<String>()

        every { car.drive(capture(slot)) } returns "ok"

        car.drive("NORTH")

        slot.isCaptured shouldBe true
        slot.captured shouldBe "NORTH"
    }
})