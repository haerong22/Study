plugins {
    id("kotlin-conventions")
    id("spring-ai-conventions")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")

    runtimeOnly("com.mysql:mysql-connector-j")
}
