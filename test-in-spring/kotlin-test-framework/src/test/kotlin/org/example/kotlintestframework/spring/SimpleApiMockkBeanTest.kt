package org.example.kotlintestframework.spring

import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [SimpleApi::class])
class SimpleApiMockkBeanTest: FunSpec() {
    override fun extensions() = listOf(SpringExtension)

    @Autowired
    private lateinit var simpleApi: SimpleApi

    @MockkBean
    private lateinit var simpleUseCase: SimpleUseCase

    init {
        test("mockkbean test") {
            every { simpleUseCase.exist() } returns true
            every { simpleUseCase.execute() } returns "TEST"

            simpleApi.getInfo() shouldBe "TEST"

            verify {
                simpleUseCase.exist()
                simpleUseCase.execute()
            }
            confirmVerified(simpleUseCase)
        }

        test("mockkbean test2") {
            every { simpleUseCase.exist() } returns false

            simpleApi.getInfo() shouldBe "NOT FOUND"

            verify {
                simpleUseCase.exist()
            }
            confirmVerified(simpleUseCase)
        }
    }
}