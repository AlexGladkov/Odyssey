plugins {
    id("multiplatform-compose-setup")
    id("android-setup")
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(project(":odyssey:odyssey-compose"))
                implementation(project(":odyssey:odyssey-core"))
            }
        }

        named("androidMain") {
            dependencies {
                implementation(Dependencies.AndroidX.AppCompat.fragmentKtx)
            }
        }
    }
}

android {
    namespace = "ru.alexgladkov.common.compose.platform"

    kotlin {
        jvmToolchain {
            languageVersion.set(JavaLanguageVersion.of("17"))
        }
    }
}