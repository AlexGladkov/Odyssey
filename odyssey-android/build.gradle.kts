plugins {
    kotlin("multiplatform")
    id(libs.plugins.compose.get().pluginId)
    id(libs.plugins.publish.get().pluginId)
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.kapt.get().pluginId)
}

group = libs.versions.packageName.get()
version = libs.versions.packageVersion.get()

kotlin {
    applyDefaultHierarchyTemplate()
    androidTarget()

    sourceSets {
        commonMain.dependencies {
            implementation(projects.odysseyCore)
            implementation(projects.odysseyCompose)
        }

        androidMain.dependencies {
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

android {
    compileSdk = libs.versions.compileSdk.get().toInt()
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

    namespace = "ru.alexgladkov.odyssey.android"

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
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