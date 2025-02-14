plugins {
    id("kotlin-conventions")
    id("spring-ai-conventions")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")

    implementation("org.jsoup:jsoup:1.14.3")
    implementation("org.springframework.ai:spring-ai-pgvector-store-spring-boot-starter")
}
