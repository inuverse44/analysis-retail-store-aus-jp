plugins {
    kotlin("jvm") version "2.2.20"
}

group = "jp.retail"
version = "1.0"

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(21)
}

// Build: gradle :lib:jar
// Use in notebooks: @file:DependsOn("../../lib/build/libs/retail-utils-1.0.jar")
tasks.jar {
    archiveBaseName.set("retail-utils")
}
