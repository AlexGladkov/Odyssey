import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    id("com.android.library")
    id("kotlin-multiplatform")
    kotlin("kapt")
}

kotlin {
    jvm("desktop")
    android()

//    val iosTarget: (String, KotlinNativeTarget.() -> Unit) -> KotlinNativeTarget = when {
//        System.getenv("SDK_NAME")?.startsWith("iphoneos") == true -> ::iosArm64
//        else -> ::iosX64
//    }
//
//    iosTarget("ios") { }

    ios()

    sourceSets {
        named("commonTest") {
            dependencies {
                implementation(Dependencies.JetBrains.Kotlin.testCommon)
                implementation(Dependencies.JetBrains.Kotlin.testAnnotationsCommon)
            }
        }

        named("desktopTest") {
            dependencies {
                implementation(Dependencies.JetBrains.Kotlin.testJunit)
            }
        }
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
}