package org.example.kotlintestframework.kotest.assertion

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain

class AssertionSoftlyExample: FunSpec({

    test("String length 는 문자열의 길이를 반환해야 한다.") {
        assertSoftly {
            "test".length shouldBe 4
            "hello".length shouldBeGreaterThan 2
        }
    }

    test("String") {
        val str = "hello"
        assertSoftly(str) {
            length shouldBe 5
            shouldContain("lo")
        }
    }
})