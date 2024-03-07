import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

plugins {
    id("com.android.library")
    kotlin("multiplatform")
}

kotlin {
    jvm()
    androidTarget {
        publishAllLibraryVariants()
    }
    js {
        browser()
    }
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    macosArm64()
    macosX64()

    jvmToolchain(17)

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += "-Xexpect-actual-classes"
        }
    }
}