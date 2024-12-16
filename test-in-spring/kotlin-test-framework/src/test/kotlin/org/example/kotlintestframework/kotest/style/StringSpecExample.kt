package org.example.kotlintestframework.kotest.style

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class StringSpecExample : StringSpec({
    "strings.length 는 문자열의 길이를 반환해야 한다." {
        "hello".length shouldBe 5
    }
})