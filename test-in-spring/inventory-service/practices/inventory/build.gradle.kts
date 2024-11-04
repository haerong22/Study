plugins {
    id("custom.java-conventions")
    id("custom.test-conventions")
    id("custom.spring-conventions")
}

dependencies {
    implementation(Spring.boot.web)
    implementation(Spring.boot.actuator)
    implementation(Spring.boot.data.jpa)

    testImplementation("com.h2database:h2")
    testImplementation("mysql:mysql-connector-java:_")
}