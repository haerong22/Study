plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
    id("io.spring.dependency-management")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:3.3.4")
    }
}

dependencies {
    // JPA 엔티티와 Bean Validation을 위한 필수 의존성
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // 페이징을 위한 Spring Data Commons
    implementation("org.springframework.data:spring-data-commons")

    // Jackson 어노테이션 사용 (WebSocketDto에서 사용)
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
}