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

    androidTarget()
    jvm("desktop")
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

                implementation(libs.coroutines.core)
                implementation(libs.kotlin.immutable)
            }
        }

        named("commonTest") {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation("junit:junit:4.13.2")
            }
        }

        named("androidMain") {
            dependencies {
                implementation(libs.activity.compose)
            }
        }

        val desktopTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13.2")
            }
        }

        val iosMain by getting {
            dependsOn(commonMain)
        }

        val iosSimulatorArm64Main by getting
        iosSimulatorArm64Main.dependsOn(iosMain)

        val macosMain by creating {
            dependsOn(commonMain)
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

    namespace = "ru.alexgladkov.odyssey.core"

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
    artifactId = "odyssey-core",
    name = "Library core"
)