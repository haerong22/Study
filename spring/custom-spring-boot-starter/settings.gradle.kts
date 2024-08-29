plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "custom-spring-boot-starter"
include("handgame")
include("handgame-spring-boot-autoconfigure")
include("handgame-spring-boot-starter")
