plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    id("io.spring.dependency-management")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:3.3.4")
    }
}

dependencies {
    implementation(project(":chat-domain"))
    implementation(project(":chat-persistence"))

    // WebSocket 기능
    implementation("org.springframework.boot:spring-boot-starter-websocket")

    // Redis 지원 (RedisMessageBroker 사용을 위해 필요)
    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    // 페이징 지원 (채팅방 목록 로드용)
    implementation("org.springframework.data:spring-data-commons")

    // JSON 직렬화/역직렬화 (WebSocket 메시지 처리용)
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
}