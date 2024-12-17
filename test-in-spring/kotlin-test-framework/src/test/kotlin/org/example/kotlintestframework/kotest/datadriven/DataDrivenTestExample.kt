package org.example.kotlintestframework.kotest.datadriven

import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe

// io.kotest:kotest-framework-datatest
class DataDrivenTestExample : FunSpec({
    context("object") {
        withData(
            PythagoreanTriangle(3, 4, 5),
            PythagoreanTriangle(5, 12, 13),
            PythagoreanTriangle(8, 15, 17),
        ) { (a, b, c) ->
            isPythagoreanTriangle(a, b, c) shouldBe true
        }
    }

    context("list") {
        withData(
            listOf(
                PythagoreanTriangle(3, 4, 5),
                PythagoreanTriangle(5, 12, 13),
                PythagoreanTriangle(8, 15, 17),
            )
        ) { (a, b, c) ->
            isPythagoreanTriangle(a, b, c) shouldBe true
        }
    }

    context("map") {
        withData(
            mapOf(
                "a=3, b=4, c=5" to PythagoreanTriangle(3, 4, 5),
                "a=5, b=12, c=13" to PythagoreanTriangle(5, 12, 13),
                "a=8, b=15, c=17" to PythagoreanTriangle(8, 15, 17),
            )
        ) { (a, b, c) ->
            isPythagoreanTriangle(a, b, c) shouldBe true
        }
    }

    context("name fn") {
        withData(
            nameFn = { "${it.a}^2 + ${it.b}^2 = ${it.c}^2"},
            PythagoreanTriangle(3, 4, 5),
            PythagoreanTriangle(5, 12, 13),
            PythagoreanTriangle(8, 15, 17),
        ) { (a, b, c) ->
            isPythagoreanTriangle(a, b, c) shouldBe true
        }
    }
})

data class PythagoreanTriangle(val a: Int, val b: Int, val c: Int)

fun isPythagoreanTriangle(a: Int, b: Int, c: Int) = a * a + b * b == c * c