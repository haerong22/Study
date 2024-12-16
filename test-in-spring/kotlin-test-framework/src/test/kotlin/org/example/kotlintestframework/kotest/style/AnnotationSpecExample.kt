package org.example.kotlintestframework.kotest.style

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe

class AnnotationSpecExample : AnnotationSpec() {

    @BeforeAll
    fun beforeAll() {
        println("AnnotationSpecExample.beforeAll")
    }

    @BeforeEach
    fun beforeEach() {
        println("AnnotationSpecExample.beforeEach")
    }

    @AfterAll
    fun afterAll() {
        println("AnnotationSpecExample.afterAll")
    }

    @AfterEach
    fun afterEach() {
        println("AnnotationSpecExample.afterEach")
    }

    @Test
    fun test1() {
        1 shouldBe 1
    }

    @Test
    fun test2() {
        2 shouldBe 2
    }
}