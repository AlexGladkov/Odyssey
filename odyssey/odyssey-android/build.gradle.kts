plugins {
    id("multiplatform-android-setup")
    id("android-setup")
    id("maven-publish")
    id("convention.publication")
    kotlin("kapt")
}

group = Dependencies.odysseyPackage
version = Dependencies.odyssey

kotlin {
    android {
        publishLibraryVariants("release", "debug")
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":odyssey:odyssey-core"))
                implementation(project(":odyssey:odyssey-compose"))
            }
        }

        androidMain {
            dependencies {
                implementation(Dependencies.Google.Dagger.hiltAndroid)
                implementation(Dependencies.AndroidX.Hilt.hiltNavigationCompose)

                configurations.getByName("kapt").dependencies.add(
                    org.gradle.api.internal.artifacts.dependencies.DefaultExternalModuleDependency(
                        "com.google.dagger",
                        "hilt-android-compiler",
                        Dependencies.Google.Dagger.version.toString()
                    )
                )
            }
        }
    }
}

android {
    namespace = "ru.alexgladkov.common.compose.android"

    kotlin {
        jvmToolchain {
            languageVersion.set(JavaLanguageVersion.of("17"))
        }
    }
}