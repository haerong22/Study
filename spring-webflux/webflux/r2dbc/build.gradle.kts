plugins {
    java
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.springframework.security:spring-security-core")
    implementation("io.asyncer:r2dbc-mysql:1.1.0")
}