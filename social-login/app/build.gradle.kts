plugins {
    id("kotlin-conventions")
    id("springboot-conventions")
}

dependencies {
    implementation(project(":controller"))
    implementation(project(":application"))
    implementation(project(":domain"))
    implementation(project(":gateway:social"))
    implementation(project(":gateway:db"))
}