plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "chat-server"

include(
    "chat-application",
    "chat-domain",
    "chat-persistence"
)