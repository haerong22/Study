package org.example.kotlintestframework.kotest.assertion

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.assertions.throwables.shouldThrowMessage
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.date.shouldBeAfter
import io.kotest.matchers.date.shouldBeBefore
import io.kotest.matchers.maps.shouldHaveKeys
import io.kotest.matchers.maps.shouldHaveSize
import io.kotest.matchers.maps.shouldHaveValues
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.optional.shouldBePresent
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldBeLowerCase
import io.kotest.matchers.string.shouldHaveLength
import io.kotest.matchers.string.shouldNotBeEmpty
import java.time.LocalDateTime
import java.util.Optional

class AssertionCoreExample: FunSpec({
    test("core") {
        "test".length.shouldBe(4)

        "test".length shouldBe 4

        "test"
            .shouldHaveLength(4)
            .shouldBeLowerCase()
            .shouldNotBeEmpty()
            .shouldNotBeNull()

        mapOf(1 to "one", 2 to "two")
            .shouldHaveSize(2)

        mapOf(1 to "one", 2 to "two")
            .shouldHaveKeys(1, 2)

        mapOf(1 to "one", 2 to "two")
            .shouldHaveValues("one", "two")

        Optional.of("test")
            .shouldBePresent()
            .shouldBe("test")

        LocalDateTime.parse("2024-02-01T00:00:00")
            .shouldBeAfter(LocalDateTime.parse("2024-01-01T00:00:00"))

        LocalDateTime.parse("2024-02-01T00:00:00")
            .shouldBeBefore(LocalDateTime.parse("2024-03-01T00:00:00"))
    }

    test("exception") {
        val exception = shouldThrow<NumberFormatException> {
            "string".toInt()
        }
        exception.message shouldBe "For input string: \"string\""

        shouldThrow<NumberFormatException> {
            "string".toInt()
        }.message shouldBe "For input string: \"string\""

        shouldThrowMessage("For input string: \"string\"") {
            "string".toInt()
        }
    }

})