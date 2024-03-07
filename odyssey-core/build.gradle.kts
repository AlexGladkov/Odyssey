plugins {
    id("multiplatform-setup")
    id(libs.plugins.publish.get().pluginId)
    id(libs.plugins.compose.get().pluginId)
}

group = libs.versions.packageName.get()
version = libs.versions.packageVersion.get()

kotlin {
    applyDefaultHierarchyTemplate()

    sourceSets {
        commonMain.dependencies {
            implementation(compose.ui)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.runtime)

            implementation(libs.uuid)
            implementation(libs.coroutines.core)
            implementation(libs.kotlin.immutable)
        }

        androidMain.dependencies {
            implementation(libs.activity.compose)
        }
    }
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

    namespace = "ru.alexgladkov.odyssey.core"

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
    artifactId = "odyssey-core",
    name = "Library core"
)