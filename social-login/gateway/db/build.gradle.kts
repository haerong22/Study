plugins {
    id("kotlin-conventions")
    id("springboot-conventions")
}

dependencies {
    implementation(project(":domain"))

    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")

    implementation("io.r2dbc:r2dbc-h2")
}
