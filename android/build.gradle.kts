plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-parcelize")
    id("org.jetbrains.compose")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdkVersion(31)

    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(31)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    packagingOptions {
        exclude("META-INF/*")
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(project(":common:common-root"))
    implementation(project(":odyssey:odyssey-compose"))
    implementation(project(":odyssey:odyssey-core"))
    implementation(project(":common:common-compose"))
    implementation(compose.material)

    implementation(Dependencies.AndroidX.AppCompat.appCompat)
    implementation(Dependencies.AndroidX.Activity.activityCompose)
    implementation(Dependencies.Images.kamel)
    implementation(Dependencies.Google.Dagger.hiltAndroid)
    kapt(Dependencies.Google.Dagger.hiltAndroidCompiler)
    implementation(Dependencies.AndroidX.Hilt.hiltNavigationCompose)
}