package org.example.kotlintestframework.kotest.lifecyclehook

import io.kotest.core.spec.style.FunSpec

class LifeCycleExample: FunSpec({
    beforeSpec {
        println("Before spec")
    }

    beforeEach {
        println("Before each")
    }

    afterSpec {
        println("After spec")
    }

    afterEach {
        println("After each")
    }


})