plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("maven-publish")
    id("com.android.library")
    kotlin("kapt")
}

group = "io.github.alexgladkov"
version = "1.4.0"

kotlin {
    android()

    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":odyssey:odyssey-core"))
                implementation(project(":odyssey:odyssey-compose"))
            }
        }

        val androidMain by getting {
            dependencies {
//                implementation(Dependencies.Google.Dagger.hiltAndroid)
//                implementation(Dependencies.AndroidX.Hilt.hiltNavigationCompose)

//                configurations.getByName("kapt").dependencies.add(
//                    org.gradle.api.internal.artifacts.dependencies.DefaultExternalModuleDependency(
//                        "com.google.dagger",
//                        "hilt-android-compiler",
//                        Dependencies.Google.Dagger.version.toString()
//                    )
//                )
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