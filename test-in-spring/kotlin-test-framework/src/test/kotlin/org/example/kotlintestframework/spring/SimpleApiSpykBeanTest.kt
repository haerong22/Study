package org.example.kotlintestframework.spring

import com.ninjasquad.springmockk.SpykBean
import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [SimpleApi::class])
class SimpleApiSpykBeanTest: FunSpec() {
    override fun extensions() = listOf(SpringExtension)

    @Autowired
    private lateinit var simpleApi: SimpleApi

    @SpykBean
    private lateinit var simpleService: SimpleService

    init {
        test("spykbean test") {
            every { simpleService.exist() } returns true

            simpleApi.getInfo() shouldBe "Hello, World!"
        }

        test("spykbean test2") {
            every { simpleService.exist() } returns true
            every { simpleService.execute() } returns "TEST"

            simpleApi.getInfo() shouldBe "TEST"
        }

        test("spykbean test3") {
            every { simpleService.exist() } returns false
            every { simpleService.execute() } returns "TEST"

            simpleApi.getInfo() shouldBe "NOT FOUND"

            verify(exactly = 0) { simpleService.execute() }
        }

        test("spykbean test4") {
            simpleApi.getInfo() shouldBe "Hello, World!"
        }

    }
}