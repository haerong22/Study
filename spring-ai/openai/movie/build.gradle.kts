plugins {
    id("kotlin-conventions")
    id("spring-ai-conventions")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")

    implementation("org.json:json:20250107")
    implementation("org.springframework.ai:spring-ai-pgvector-store-spring-boot-starter")
}
