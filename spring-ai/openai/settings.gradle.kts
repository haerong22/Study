plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

rootProject.name = "openai"
include("basic")
include("chat")
include("image")
include("math")
include("recipe")
include("stt")
include("tts")
include("sql")
include("rag")
include("movie")
include("hotel")
include("sticker-chatbot")
