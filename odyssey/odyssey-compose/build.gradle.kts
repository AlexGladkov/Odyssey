import org.jetbrains.compose.compose

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("maven-publish")
    id("com.android.library")
    id("kotlin-parcelize")
}

group = libs.versions.packageName.get()
version = libs.versions.packageVersion.get()

kotlin {
    jvm("desktop")
    androidTarget {
        publishLibraryVariants("release")
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    js {
        browser()
        binaries.executable()
    }

    macosX64()
    macosArm64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.ui)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.runtime)

                implementation(libs.uuid)
                implementation(libs.kotlin.immutable)

                implementation(project(":odyssey:odyssey-core"))
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        val desktopMain by getting {
            dependencies {
                implementation(libs.coroutines.swing)
                implementation(compose.desktop.common)
            }
        }

        val desktopTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13.2")
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.coroutines.android)
                implementation(libs.activity.compose)
            }
        }
    }
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

    namespace = "ru.alexgladkov.odyssey.compose"

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin {
        jvmToolchain {
            languageVersion.set(JavaLanguageVersion.of("17"))
        }
    }
}

configureMavenPublication(
    groupId = libs.versions.packageName.get(),
    artifactId = "odyssey-compose",
    name = "Compose implementation for core"
)