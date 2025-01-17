plugins {
    kotlin("jvm")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("io.projectreactor:reactor-bom:2024.0.1"))
    implementation("io.projectreactor:reactor-core")
}
