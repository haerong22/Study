pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

rootProject.name = "webflux"
include("user")
include("image")
include("sse")
include("websocket")
include("websocket-coroutine")
include("r2dbc")
include("r2dbc-coroutine")
