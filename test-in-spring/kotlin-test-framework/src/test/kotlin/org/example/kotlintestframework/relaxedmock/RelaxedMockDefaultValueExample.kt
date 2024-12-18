package org.example.kotlintestframework.relaxedmock

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.optional.shouldNotBePresent
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class RelaxedMockDefaultValueExample : FunSpec({

    test("relaxed mock") {
        val car = mockk<RelaxedCar>(relaxed = true)

        car.drive("NORTH") shouldBe ""

        car.getInt() shouldBe 0

        car.getFloat() shouldBe 0.0

        car.isBoolean() shouldBe false

        car.getOptional().shouldNotBePresent()

        car.getList().shouldBeEmpty()

        assertSoftly(car.getObject()) {
            it.name shouldBe ""
            it.age shouldBe 0
            it.hasLicense shouldBe false
        }

        car.stop()
    }

    test("relaxUnitFun") {
        val car = mockk<RelaxedCar>(relaxUnitFun = true)

        every { car.drive(any()) } returns "ok"

        car.drive("NORTH") shouldBe "ok"

        car.stop()
    }
})