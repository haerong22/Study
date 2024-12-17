package org.example.kotlintestframework.kotest.assertion

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldContainInOrder
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.collections.shouldNotContain

class AssertionCollectionExample : FunSpec({

    test("Collection test") {
        val list = listOf(1, 2, 3, 4, 5)

        list shouldHaveSize 5
        list shouldContain 3
        list shouldNotContain 6
        list shouldContainAll listOf(1, 2, 3)
        list shouldContainExactly listOf(1, 2, 3, 4, 5)
        list shouldContainInOrder listOf(1, 2, 3)

        5 shouldBeIn list
    }
})