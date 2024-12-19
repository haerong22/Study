package org.example.kotlintestframework.spring

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.verify
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
class SimpleApiIntTest(
    private val mockMvc: MockMvc,
    private val objectMapper: ObjectMapper,
) : DescribeSpec() {
    override fun extensions() = listOf(SpringExtension)

    @MockkBean
    private lateinit var simpleUseCase: SimpleUseCase

    init {
        describe("GET /api/simple") {
            context("simple 이 존재하면") {
                every { simpleUseCase.exist() } returns true
                every { simpleUseCase.execute() } returns "TEST"

                it("'TEST' 를 반환") {
                    mockMvc.get("/api/simple")
                        .andExpect {
                            status { isOk() }
                            content { string("TEST") }
                        }
                        .andDo {
                            print()
                        }

                    verify {
                        simpleUseCase.exist()
                        simpleUseCase.execute()
                    }
                    confirmVerified(simpleUseCase)
                }
            }

            context("simple 이 존재하지 않으면") {
                every { simpleUseCase.exist() } returns false

                it("'NOT FOUND' 를 반환") {
                    mockMvc.get("/api/simple")
                        .andExpect {
                            status { isOk() }
                            content { string("NOT FOUND") }
                        }
                        .andDo {
                            print()
                        }

                    verify(exactly = 0) { simpleUseCase.execute() }
                }
            }
        }

        describe("POST /api/simple") {
            context("요청을 보내면") {
                it("200 OK 반환") {
                    val request = mapOf("name" to "TEST")
                    every { simpleUseCase.create(any()) } returns "TEST"

                    mockMvc
                        .post("/api/simple") {
                            contentType = MediaType.APPLICATION_JSON
                            content = objectMapper.writeValueAsString(request)
                        }
                        .andExpect {
                            status { isOk() }
                        }

                    verify {
                        simpleUseCase.create(
                            withArg { it.name shouldBe "TEST" }
                        )
                    }
                }
            }

            context("허용되지 않는 name 이면") {
                it("400 bad request") {
                    val request = mapOf("name" to "")
                    every { simpleUseCase.create(any()) } throws IllegalArgumentException()

                    mockMvc
                        .post("/api/simple") {
                            contentType = MediaType.APPLICATION_JSON
                            content = objectMapper.writeValueAsString(request)
                        }
                        .andExpect {
                            status { isBadRequest() }
                        }
                }
            }
        }
    }
}