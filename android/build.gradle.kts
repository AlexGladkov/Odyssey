plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-parcelize")
}

android {
    compileSdkVersion(Dependencies.compileSdk)

    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(Dependencies.targetSdk)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.0"
    }

    lintOptions {
        isCheckReleaseBuilds = false
    }

    packagingOptions {
        exclude("META-INF/*")
    }
}

dependencies {
    implementation(project(":common:common-sample"))
    implementation(project(":odyssey:odyssey-compose"))
    implementation(project(":odyssey:odyssey-core"))

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.activity:activity-compose:1.6.1")
    implementation(Dependencies.Images.kamel)

    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.3.0")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.3.0")
}