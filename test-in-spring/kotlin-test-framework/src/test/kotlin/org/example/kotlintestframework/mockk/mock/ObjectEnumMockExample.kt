package org.example.kotlintestframework.mockk.mock

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockkObject

class ObjectEnumMockExample: FunSpec({

    test("object mock") {
        mockkObject(ObjMock)
        every { ObjMock.add(1, 2) } returns 100

        ObjMock.add(1, 2) shouldBe 100
    }

    test("enum mock") {
        mockkObject(EnumMock.CONSTANT)
        every { EnumMock.CONSTANT.value } returns 100

        EnumMock.CONSTANT.value shouldBe 100
    }
})

object ObjMock {
    fun add(a: Int, b: Int) = a + b
}

enum class EnumMock(val value: Int) {
    CONSTANT(35),
}