plugins {
    id("org.sonarqube")
}

sonar {
    properties {
        property("sonar.organization", "haerong22")
        property("sonar.host.url", "https://sonarcloud.io")
    }
}