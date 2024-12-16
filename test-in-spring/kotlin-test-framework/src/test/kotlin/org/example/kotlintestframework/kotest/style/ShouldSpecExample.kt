package org.example.kotlintestframework.kotest.style

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import kotlin.test.expect

class ShouldSpecExample : ShouldSpec({
    context("String length") {
        should("String length 는 문자열의 길이를 반환해야 한다.") {
            "test".length shouldBe 4
            "".length shouldBe 0
        }
    }

    context("toInt") {

        should("숫자 형태 문자를 숫자로 변환한다.") {
            "12345".toInt() shouldBe 12345
        }

        should("숫자형태가 아니면 exception") {
            shouldThrow<NumberFormatException> {
                "string".toInt()
            }
        }
    }
})