import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("jvm")
    kotlin("plugin.spring")
}

group = "org.example.delivery"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

extra["springCloudVersion"] = "2023.0.0"

dependencies {
    if (isAppleSilicon()) {
        runtimeOnly("io.netty:netty-resolver-dns-native-macos:4.1.94.Final:osx-aarch_64")
    }

    implementation(project(":common"))

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.springframework.cloud:spring-cloud-starter-gateway")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

fun isAppleSilicon() = System.getProperty("os.name") == "Mac OS X" && System.getProperty("os.arch") == "aarch64"

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}