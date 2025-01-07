plugins {
    id("kotlin-conventions")
    id("springboot-conventions")
}

dependencies {
    implementation(project(":domain"))

    implementation("org.springframework.boot:spring-boot-starter-webflux")
}