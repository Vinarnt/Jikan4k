pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }

}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.10.0"
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "jikan4k-generator"
include(":library")
project(":library").name = "jikan4k"
