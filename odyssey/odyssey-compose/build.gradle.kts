import org.jetbrains.compose.compose

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("maven-publish")
    id("com.android.library")
}

group = libs.versions.packageName.get()
version = libs.versions.packageVersion.get()

kotlin {
    jvm("desktop")
    android {
        publishLibraryVariants("release")
    }
    ios()
    iosSimulatorArm64()
    js(IR) {
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

        val commonButJSMain by creating {
            dependsOn(commonMain)
        }
        val skikoMain by creating {
            dependsOn(commonMain)
        }
        val jvmAndAndroidMain by creating {
            dependsOn(commonMain)
        }
        val nativeMain by creating {
            dependsOn(commonMain)
        }

        val desktopMain by getting {
            dependsOn(skikoMain)
            dependsOn(jvmAndAndroidMain)
            dependsOn(commonButJSMain)

            dependencies {
                implementation(libs.coroutines.swing)
                implementation(compose.desktop.common)
            }
        }

        val androidMain by getting {
            dependsOn(jvmAndAndroidMain)
            dependsOn(commonButJSMain)

            dependencies {
                implementation(libs.coroutines.android)
                implementation(libs.activity.compose)
            }
        }

        val iosMain by getting {
            dependsOn(skikoMain)
            dependsOn(commonButJSMain)
            dependsOn(nativeMain)
        }

        val iosTest by getting
        val iosSimulatorArm64Main by getting
        iosSimulatorArm64Main.dependsOn(iosMain)
        val iosSimulatorArm64Test by getting
        iosSimulatorArm64Test.dependsOn(iosTest)
        val jsMain by getting {
            dependsOn(skikoMain)
        }
        val macosMain by creating {
            dependsOn(skikoMain)
            dependsOn(commonButJSMain)
            dependsOn(nativeMain)
        }
        val macosX64Main by getting {
            dependsOn(macosMain)
        }
        val macosArm64Main by getting {
            dependsOn(macosMain)
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
    artifactId = "odyssey-compose",
    name = "Compose implementation for core"
)