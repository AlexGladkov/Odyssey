import org.jetbrains.compose.compose

plugins {
    id("multiplatform-setup")
    id("android-setup")
    id("maven-publish")
    id("convention.publication")
}

group = "io.github.alexgladkov"
version = "0.1.4"

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