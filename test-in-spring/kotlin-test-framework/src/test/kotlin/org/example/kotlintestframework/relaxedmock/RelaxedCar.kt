package org.example.kotlintestframework.relaxedmock

import java.util.Optional

class RelaxedCar {
    fun drive(direction: String): String {
        TODO("Not Implemented")
    }

    fun getInt(): Int {
        TODO("Not Implemented")
    }

    fun getFloat(): Float {
        TODO("Not Implemented")
    }

    fun isBoolean(): Boolean {
        TODO("Not Implemented")
    }

    fun getOptional(): Optional<Driver> {
        TODO("Not Implemented")
    }

    fun getList(): List<Driver> {
        TODO("Not Implemented")
    }

    fun getObject(): Driver {
        TODO("Not Implemented")
    }

    fun stop(): Unit {
        println("stop")
    }
}

class Driver(
    val name: String,
    val age: Int,
    val hasLicense: Boolean,
)