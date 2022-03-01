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
                implementation(Dependencies.Utils.UUID)
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