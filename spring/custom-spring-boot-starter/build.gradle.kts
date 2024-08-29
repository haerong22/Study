import org.springframework.boot.gradle.plugin.SpringBootPlugin

plugins {
	id("org.springframework.boot") version "3.3.3"
	id("io.spring.dependency-management") version "1.1.6"
	id("maven-publish")

	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	kotlin("kapt") version "1.9.25"
}

allprojects {
	group = "org.example"
	version = "1.0-SNAPSHOT"

	repositories {
		mavenCentral()
		mavenLocal()
	}
}

subprojects {
	apply(plugin = "kotlin")
	apply(plugin = "kotlin-kapt")
	apply(plugin = "kotlin-spring")
	apply(plugin = "maven-publish")
	apply(plugin = "io.spring.dependency-management")

	dependencies {
		implementation("org.jetbrains.kotlin:kotlin-reflect")
		implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	}

	dependencyManagement {
		imports {
			mavenBom(SpringBootPlugin.BOM_COORDINATES)
		}
	}

	java {
		toolchain {
			languageVersion = JavaLanguageVersion.of(17)
		}
	}

	kotlin {
		compilerOptions {
			freeCompilerArgs.addAll("-Xjsr305=strict")
		}
	}
}






