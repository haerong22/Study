plugins {
    kotlin("jvm") version "1.9.22"
    id("me.champeau.jmh") version "0.7.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}

jmh {
    threads = 1
    fork = 1
    warmupIterations = 1
    iterations = 1
}