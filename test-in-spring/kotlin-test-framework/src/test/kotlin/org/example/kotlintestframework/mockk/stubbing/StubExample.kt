package org.example.kotlintestframework.mockk.stubbing

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.justRun
import io.mockk.mockk
import org.example.kotlintestframework.mockk.Car

class StubExample: FunSpec({

    test("stub") {
        val car = mockk<Car>()

        every { car.drive("NORTH") } returns "ok"

        car.drive("NORTH") shouldBe "ok"
    }

    test("stub arg matching any") {
        val car = mockk<Car>()

        every { car.drive(any()) } returns "ok"

        car.drive("NORTH") shouldBe "ok"
    }

    test("stub arg matching class") {
        val car = mockk<Car>()

        every { car.drive(any(String::class)) } returns "ok"

        car.drive("NORTH") shouldBe "ok"
    }

    test("stub arg matching eq") {
        val car = mockk<Car>()

        every { car.drive(eq("NORTH")) } returns "ok"

        car.drive("NORTH") shouldBe "ok"
    }

    test("stub return many") {
        val car = mockk<Car>()

        every { car.drive(any(String::class)) } returnsMany listOf("ok", "error")

        car.drive("NORTH") shouldBe "ok"
        car.drive("NORTH") shouldBe "error"
    }

    test("stub return arg") {
        val car = mockk<Car>()

        every { car.drive(any()) } returnsArgument 0

        car.drive("NORTH") shouldBe "NORTH"
    }

    test("stub return Unit") {
        val mockClass = mockk<MockClass>()

        every { mockClass.sum(any(), any()) } returns Unit
        every { mockClass.sum(any(), any()) } just Runs
        justRun { mockClass.sum(any(), any()) }

        mockClass.sum(1, 2) shouldBe Unit
    }
})

class MockClass {
    fun sum(a: Int, b: Int) {
        println(a + b)
    }
}