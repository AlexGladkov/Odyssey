import org.jetbrains.compose.compose

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("maven-publish")
    id("com.android.library")
}

group = "io.github.alexgladkov"
version = "1.4.0"

kotlin {
    jvm("desktop")
    android()
    ios()
    iosSimulatorArm64()
    js(IR) {
        browser()
    }
    macosX64()
    macosArm64()

    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(compose.ui)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.runtime)
                implementation(libs.uuid)

                implementation(project(":odyssey:odyssey-core"))
            }
        }
        named("commonTest")
        named("androidMain") {
            dependencies {
                implementation(libs.coroutines.android)
                implementation(libs.activity.compose)
            }
        }

        val desktopMain by getting {
            dependencies {
                implementation(libs.coroutines.swing)
            }
        }
    }
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlin {
        jvmToolchain {
            languageVersion.set(JavaLanguageVersion.of("11"))
        }
    }
}