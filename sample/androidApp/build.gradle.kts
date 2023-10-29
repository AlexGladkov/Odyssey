plugins {
    id("com.android.application")
    kotlin("android")
    id("org.jetbrains.compose")
}

dependencies {
    implementation(project(":sample:shared"))
    implementation(project(":odyssey:odyssey-compose"))
    implementation(project(":odyssey:odyssey-core"))

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
        minSdk = 24
        targetSdk = 34
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
