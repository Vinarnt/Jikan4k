import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `maven-publish`
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.android.library)
    alias(libs.plugins.openapi.generator)
    alias(libs.plugins.ktlint)
}

group = "fr.vinarnt"
version = "1.0.0"

kotlin {
    jvmToolchain(11)

    jvm()
    androidTarget {
        publishLibraryVariants("release")
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    linuxX64()
    js {
        browser()
        nodejs()
    }
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        nodejs()
    }

    sourceSets {
        val commonMain by getting {
            kotlin {
                srcDir(layout.buildDirectory.dir("generate-resources/main/src/commonMain/kotlin"))
            }

            dependencies {
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.kotlinx.serialization.core)
                implementation(libs.kotlinx.datetime)
                implementation(libs.bundles.ktor.common)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(libs.ktor.client.cio)
            }
        }

        val linuxX64Main by getting {
            dependencies {
                implementation(libs.ktor.client.cio)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.ktor.client.cio)
            }
        }

        val iosX64Main by getting {
            dependencies {
                implementation(libs.ktor.client.ios)
            }
        }

        val iosArm64Main by getting {
            dependencies {
                implementation(libs.ktor.client.ios)
            }
        }

        val iosSimulatorArm64Main by getting {
            dependencies {
                implementation(libs.ktor.client.ios)
            }
        }

        val jsMain by getting {
            dependencies {
                implementation(libs.ktor.client.js)
            }
        }

        val wasmJsMain by getting {
            dependencies {
                implementation(libs.ktor.client.js)
            }
        }
    }
}

android {
    namespace = "org.jetbrains.kotlinx.multiplatform.library.template"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

openApiGenerate {
    inputSpec.set(layout.projectDirectory.file("api-docs.json").toString())
    templateDir.set(layout.projectDirectory.dir("templates").toString())
    generatorName.set("kotlin")
    library.set("multiplatform")
    packageName.set("fr.vinarnt.jikan4k")
    ignoreFileOverride.set(file(".openapi-generator-ignore").toString())
    cleanupOutput.set(true)
    configOptions.putAll(
        mapOf(
            "dateLibrary" to "kotlinx-datetime",
            "enumPropertyNaming" to "UPPERCASE",
            "omitGradleWrapper" to "true",
            "nonPublicApi" to "true"
        )
    )
    generateApiTests.set(false)
    generateModelTests.set(false)
    generateApiDocumentation.set(false)
    generateModelDocumentation.set(false)
    openapiNormalizer.putAll(
        mapOf(
            "REF_AS_PARENT_IN_ALLOF" to "false",
            "REFACTOR_ALLOF_WITH_PROPERTIES_ONLY" to "true",
        ),
    )
}

ktlint {
    outputToConsole.set(false)
    coloredOutput.set(true)
    filter {
        include("**/generate-resources/main/src/commonMain/kotlin/**")
    }
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/Vinarnt/Jikan4k")
            credentials {
                username = System.getenv("GITHUB_ACTOR") ?: System.getenv("USERNAME")
                password = System.getenv("GITHUB_TOKEN") ?: System.getenv("TOKEN")
            }
        }
    }

    publications {
        filterIsInstance<MavenPublication>().forEach { publication ->
            with(publication) {
                pom {
                    name = "Jikan4k"
                    description = "Auto generated API client for jikan API"
                    url = "https://github.com/Vinarnt/Jikan4k"
                    licenses {
                        license {
                            name = "The Unlicense"
                            url = "https://unlicense.org/"
                        }
                    }
                    scm {
                        connection = "scm:git:git://github.com/Vinarnt/Jikan4k.git"
                        developerConnection = "scm:git:ssh:github.com:Vinarnt/Jikan4k.git"
                        url = "https://github.com/Vinarnt/Jikan4k"
                    }
                }
            }
        }
    }
}

tasks {
    named("runKtlintFormatOverCommonMainSourceSet") {
        inputs.dir(layout.buildDirectory.dir("generate-resources/main/src/commonMain/kotlin"))
    }

    named("openApiGenerate") {
        finalizedBy("runKtlintFormatOverCommonMainSourceSet")
    }
}
