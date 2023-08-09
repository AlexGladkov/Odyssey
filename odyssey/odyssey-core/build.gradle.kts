import org.jetbrains.compose.compose

plugins {
    id("multiplatform-compose-setup")
    id("android-setup")
    id("maven-publish")
    id("convention.publication")
}

group = Dependencies.odysseyPackage
version = Dependencies.odyssey

kotlin {
    android {
        publishLibraryVariants("release", "debug")
    }

    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(Dependencies.JetBrains.Kotlin.coroutines)
            }
        }
        named("commonTest")

        named("androidMain") {
            dependencies {
                implementation(Dependencies.AndroidX.Activity.activityCompose)
            }
        }
    }
}

android {
    namespace = "ru.alexgladkov.odyssey.core"

    kotlin {
        jvmToolchain {
            languageVersion.set(JavaLanguageVersion.of("17"))
        }
    }
}