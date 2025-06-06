plugins {
    id("custom.java-conventions")
    id("custom.test-conventions")
    id("custom.spring-conventions")
    id("custom.jacoco-conventions")
    id("custom.sonar-conventions")
}

dependencies {
    implementation(Spring.boot.web.toString()) {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-tomcat")
    }
    implementation("org.springframework.boot:spring-boot-starter-undertow")
    implementation(Spring.boot.actuator)
    implementation(Spring.boot.data.jpa)
    implementation(Spring.boot.data.redis)

    implementation(Spring.cloud.stream.stream)
    implementation(Spring.cloud.stream.binderKafka)

    implementation("mysql:mysql-connector-java:_")

    // test
    testImplementation("com.h2database:h2")

    // testcontainers
    testImplementation(platform("org.testcontainers:testcontainers-bom:_"))
    testImplementation("org.testcontainers:testcontainers")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:mysql")
    testImplementation("org.testcontainers:kafka")

    // archunit
    testImplementation("com.tngtech.archunit:archunit-junit5:_")

    // kafka
    testImplementation("org.springframework.kafka:spring-kafka-test")

    // spring-cloud-stream
//    testImplementation("org.springframework.cloud:spring-cloud-stream-test-binder")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:2023.0.1")
    }
}

sonar {
    properties {
        property("sonar.projectKey", "haerong22_Study")
        property("sonar.projectName", "inventory-service")

        property("sonar.sources", "src/main/java")
        property("sonar.tests", "src/test/java")
        property(
            "sonar.coverage.jacoco.xmlReportPaths",
            "build/reports/jacoco/test/jacocoTestReport.xml",
        )
        property(
            "sonar.junit.reportPaths",
            "build/test-results/test"
        )
    }
}