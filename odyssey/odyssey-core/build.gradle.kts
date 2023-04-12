plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("maven-publish")
    id("com.android.library")
}

group = libs.versions.packageName.get()
version = libs.versions.packageVersion.get()

kotlin {
    android()
    jvm("desktop")
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

                implementation(libs.coroutines.core)
                implementation(libs.kotlin.immutable)
            }
        }
        named("commonTest")

        named("androidMain") {
            dependencies {
                implementation(libs.activity.compose)
            }
        }
    }
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

    namespace = "ru.alexgladkov.odyssey.core"

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

configureMavenPublication(
    groupId = libs.versions.packageName.get(),
    artifactId = "odyssey-core",
    name = "Library core"
)