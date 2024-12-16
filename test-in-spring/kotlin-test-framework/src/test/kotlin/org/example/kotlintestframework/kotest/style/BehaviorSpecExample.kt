package org.example.kotlintestframework.kotest.style

import io.kotest.core.spec.style.BehaviorSpec

class BehaviorSpecExample: BehaviorSpec({
    context("빗자루는 하늘을 날 수 있고, 주인에게 되돌아 와야 한다.") {
        given("빗자루가 주어지면") {
            `when`("빗자루 위에 앉았을 때") {
                then("날 수 있어야 한다.") {
                    // test code
                }
            }
            `when`("빗자루를 멀리 던지면") {
                then("원래 위치로 되돌아온다.") {
                    // test code
                }
            }
        }
    }

    Given("빗자루가 주어지면") {
        When("빗자루 위에 앉았을 때") {
            Then("날 수 있어야 한다.") {
                // test code
            }
        }
        When("빗자루를 멀리 던지면") {
            Then("원래 위치로 되돌아온다.") {
                // test code
            }
        }
    }
})