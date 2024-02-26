plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("maven-publish")
    id("com.android.library")
    kotlin("kapt")
}

group = libs.versions.packageName.get()
version = libs.versions.packageVersion.get()

kotlin {
    androidTarget()

    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":odyssey:odyssey-core"))
                implementation(project(":odyssey:odyssey-compose"))
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.hilt.android)
                implementation(libs.hilt.compose)

                configurations.getByName("kapt").dependencies.add(
                    org.gradle.api.internal.artifacts.dependencies.DefaultExternalModuleDependency(
                        "com.google.dagger",
                        "hilt-android-compiler",
                        libs.versions.hiltVersion.get()
                    )
                )
            }
        }
    }
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

    namespace = "ru.alexgladkov.odyssey.android"

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
    artifactId = "odyssey-android",
    name = "Android extensions for Odyssey"
)