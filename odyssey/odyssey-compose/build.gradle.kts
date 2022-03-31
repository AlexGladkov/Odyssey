import org.jetbrains.compose.compose

plugins {
    id("multiplatform-compose-setup")
    id("android-setup")
    id("maven-publish")
    id("convention.publication")
}

group = Dependencies.odysseyPackage
version = Dependencies.odyssey
dependencies {
    implementation("androidx.compose.ui:ui-unit:1.0.5")
}

kotlin {
    android {
        publishLibraryVariants("release", "debug")
    }

    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(project(":odyssey:odyssey-core"))
                implementation(Dependencies.Utils.UUID)
            }
        }
        named("commonTest")
        named("androidMain") {
            dependencies {
                implementation(Dependencies.JetBrains.Kotlin.coroutinesAndroid)
                implementation(Dependencies.AndroidX.Activity.activityCompose)
            }
        }

        desktopMain {
            dependencies {
                implementation(Dependencies.JetBrains.Kotlin.coroutinesSwing)
            }
        }
    }
}