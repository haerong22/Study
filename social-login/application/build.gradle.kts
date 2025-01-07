plugins {
    id("kotlin-conventions")
    id("springboot-conventions")
    id("reactive-conventions")
}

dependencies {
    implementation(project(":domain"))
}