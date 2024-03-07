plugins {
    id("com.android.application")
    kotlin("android")
    id(libs.plugins.compose.get().pluginId)
}

dependencies {
    implementation(projects.sample.shared)
    implementation(projects.odysseyCompose)
    implementation(projects.odysseyCore)

    implementation(compose.material)

    implementation(libs.activity.appcompat)
    implementation(libs.activity.compose)

    androidTestImplementation(libs.test.compose.junit)
    debugImplementation(libs.test.compose.manifest)
}

android {
    compileSdk = 34
    sourceSets["main"].manifest.srcFile("src/main/AndroidManifest.xml")
    namespace = "ru.alexgladkov.odyssey_demo"

    defaultConfig {
        applicationId = "ru.alexgladkov.odyssey_demo"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
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
