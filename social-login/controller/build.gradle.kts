plugins {
    id("kotlin-conventions")
    id("springboot-conventions")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation(project(":application"))
}